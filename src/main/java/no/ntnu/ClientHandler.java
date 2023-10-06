package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handle one TCP client connection. Allows up to multiple clients.
 */
public class ClientHandler {

  private final SmartTv smartTv;
  private final Socket clientSocket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  /**
   * Create a new client handler.
   *
   * @param smartTv      the instance of a smartTV.
   * @param clientSocket the TCP socket associated with this client.
   */
  public ClientHandler(SmartTv smartTv, Socket clientSocket) {
    this.smartTv = smartTv;
    this.clientSocket = clientSocket;
    System.out.println("Client connected from " + clientSocket.getRemoteSocketAddress()
        + ", port" + clientSocket.getPort());
  }

  /**
   * Run the handling logic of this TCP client.
   */
  public void run() {
    if (establishedStreams()) {
      handleClientRequest();
      closeSocket();
    }
    System.out.println("Exiting the handler of the client "
        + clientSocket.getRemoteSocketAddress());
  }

  /**
   * Establish the input and output streams of the socket.
   *
   * @return true on success, false on error.
   */
  private boolean establishedStreams() {
    boolean success = false;
    try {
      this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
      success = true;
    } catch (IOException e) {
      System.err.println("Error while processing the client: " + e.getMessage());
    }
    return success;
  }

  private void handleClientRequest() {
    String command;
    boolean shouldContinue;
    do {
      command = receiveClientCommand();
      shouldContinue = handleCommand(command);
    } while (shouldContinue);
  }

  /**
   * Receive one command from the client (over the TCP socket).
   *
   * @return The clint command, null on error.
   */
  private String receiveClientCommand() {
    String command = null;
    try {
      command = socketReader.readLine();
    } catch (IOException e) {
      System.err.println("Error while receiving data form the client: " + e.getMessage());
    }
    return command;
  }

  private boolean handleCommand(String command) {
    boolean shouldContinue = true;
    System.out.println("Command from the client: " + command);

    String response = null;

    if (command == null) {
      shouldContinue = false;
    } else {
      String[] commandParts = command.split("", 2);
      if (commandParts.length >= 1) {
        String commandType = commandParts[0];
        switch (commandType) {
          case "c":
            response = handleResponse();
            break;
          default:
            response = "Unknown command";
        }
      }
    }
    if (response != null) {
      sendToClient(response);
    }
    return shouldContinue;
  }

  //TODO: handle response
  private String handleResponse() {
    return null;
  }

  private void sendToClient(String message) {
    try {
      this.socketWriter.println(message);
    } catch (Exception e) {
      System.err.println("Error while sending a message tot he client " + e.getMessage());
    }
  }

  private void closeSocket() {
    try {
      this.clientSocket.close();
    } catch (IOException e) {
      System.err.println("Error while closing socket for client "
          + this.clientSocket.getRemoteSocketAddress() + ", reason: " + e.getMessage());
    }
  }

}
