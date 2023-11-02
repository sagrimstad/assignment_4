package no.ntnu.tv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.message.Message;

/**
 * Handles the TCP server socket(s).
 * 
 * @author  Group 2
 * @version v3.0 (2023.11.02)
 */
public class TvServer {
  public static final int PORT_NUMBER = 10025;
  private final TvLogic logic;

  boolean isTcpServerRunning;
  private final List<ClientHandler> connectedClients = new ArrayList<>();

  /**
   * Constructs an instance of the TvServer class.
   * 
   * @param logic The logic to be used in the TV
   */
  public TvServer(TvLogic logic) {
    this.logic = logic;
  }

  /**
   * Start TCP server for this TV.
   */
  public void startServer() {
    ServerSocket listeningSocket = openListeningSocket();
    if (listeningSocket != null) {
      System.out.println("Server listening on port " + PORT_NUMBER);
      isTcpServerRunning = true;
      while (isTcpServerRunning) {
        ClientHandler clientHandler = acceptNextClientConnection(listeningSocket);
        if (clientHandler != null) {
          connectedClients.add(clientHandler);
          clientHandler.start();
        }
      }
    }
  }

  /**
   * Returns a listening socket after it has been opened or null on error.
   * 
   * @return A listening socket after it has been opened or null on error
   */
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
   * Returns the client handler for a client after the connection to the client has been accepted.
   * 
   * @param listeningSocket A specified listening socket
   * @return The client handler for a client after the connection to the client has been accepted
   */
  private ClientHandler acceptNextClientConnection(ServerSocket listeningSocket) {
    ClientHandler clientHandler = null;
    try {
      Socket clientSocket = listeningSocket.accept();
      System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
      clientHandler = new ClientHandler(clientSocket, this);
    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
    }
    return clientHandler;
  }

  /**
   * Get the associated TV logic.
   *
   * @return The TV logic
   */
  public TvLogic getTvLogic() {
    return logic;
  }

  /**
   * Send a message to all currently connected clients.
   *
   * @param message The message to send
   */
  public void sendResponseToAllClients(Message message) {
    for (ClientHandler clientHandler : connectedClients) {
      clientHandler.sendToClient(message);
    }
  }

  /**
   * Removes the client handler for a client after the client has been disconnected.
   * 
   * @param clientHandler A specified client handler
   */
  public void clientDisconnected(ClientHandler clientHandler) {
    connectedClients.remove(clientHandler);
  }
}