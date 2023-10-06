package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A smart TV. Acts as a TCP <b>server</b>.
 */
public class SmartTv {

  public static final int PORT_NUMBER = 1238;
  public static final String CHANNEL_COUNT_COMMAND = "c";
  public static final String TURN_ON_COMMAND = "1";
  public static final String OK_RESPONSE = "o";
  boolean isTvOn;
  final int numberOfChannels;
  int currentChannel;
  boolean isTcpServerRunning;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private Socket socket;
  private ServerSocket serverSocket;

  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has
   */
  public SmartTv(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }

  public static void main(String[] args) {
    SmartTv tv = new SmartTv(13);
    tv.startServer();
    tv.run();
  }


  /**
   * Start TCP server for this TV.
   */
  private void startServer() {
    ServerSocket listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + PORT_NUMBER);
    if (listeningSocket != null) {
      this.isTcpServerRunning = true;
      while (this.isTcpServerRunning) {
        Socket clientSocket = acceptNextClientConnection(listeningSocket);
        if (clientSocket != null) {
          System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
          handleClient(clientSocket);
        }
      }
    }
  }

  private void run() {
    if (openListeningSocket1()) {
      this.isTvOn = true;
      while (this.isTvOn) {
        Socket clientSocket = acceptNextClient();
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.run();
      }
    }
    System.out.println("Server exiting...");
  }

  private ServerSocket openListeningSocket() {
    ServerSocket listeningSocket = null;
    try {
      listeningSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException e) {
      System.err.println("Could not open server socket: " + e.getMessage());
    }
    return listeningSocket;
  }

  /**
   * Open a listening TCP socket.
   *
   * @return true on success, false on error.
   */
  private boolean openListeningSocket1() {
    boolean success = false;
    try {
      this.serverSocket = new ServerSocket(PORT_NUMBER);
      System.out.println("Server listening on port " + PORT_NUMBER);
      success = true;
    } catch (IOException e) {
      System.err.println("Could not open a listening socket on port " + PORT_NUMBER
          + ", reason: " + e.getMessage());
    }
    return success;
  }

  private Socket acceptNextClient() {
    Socket clientSocket = null;
    try {
      clientSocket = this.serverSocket.accept();
    } catch (IOException e) {
      System.err.println("Could not accept the next client: " + e.getMessage());
    }
    return clientSocket;
  }

  private Socket acceptNextClientConnection(ServerSocket listeningSocket) {
    Socket clientSocket = null;
    try {
      clientSocket = listeningSocket.accept();
      socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
    }
    return clientSocket;
  }


  private void handleClient(Socket clientSocket) {
    String response;
    do {
      String clientRequest = readClientRequest();
      System.out.println("Received from client: " + clientRequest);
      response = handleClientRequest(clientRequest);
      if (response != null) {
        sendResponseToClient(response);
      }
    } while (response != null);
  }

  /**
   * Read one message from the TCP socket - from the client.
   *
   * @return The received client message, or null on error
   */
  private String readClientRequest() {
    String clientRequest = null;
    try {
      clientRequest = socketReader.readLine();
    } catch (IOException e) {
      System.err.println("Could not receive client request: " + e.getMessage());
    }
    return clientRequest;
  }


  private String handleClientRequest(String clientRequest) {
    String response = null;

    if (clientRequest != null) {
      if (clientRequest.equals(CHANNEL_COUNT_COMMAND)) {
        response = handleChannelCountCommand();
      } else if (clientRequest.equals(TURN_ON_COMMAND)) {
        response = handleTurnOnCommand();
      }
    }

    return response;
  }

  private String handleTurnOnCommand() {
    isTvOn = true;
    return OK_RESPONSE;
  }

  private String handleChannelCountCommand() {
    String response;
    if (isTvOn) {
      response = "c" + numberOfChannels;
    } else {
      response = "eMust turn the TV on first";
    }
    return response;
  }

  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param response The response to send to the client, NOT including the newline
   */
  private void sendResponseToClient(String response) {
    this.socketWriter.println(response);
  }

  /**
   * Shuts down the server.
   */
  public void shutdown() {
    this.isTvOn = false;
  }
}
