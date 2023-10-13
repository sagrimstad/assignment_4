package no.ntnu.communication;

import static no.ntnu.TvLogic.PORT_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A remote control for a TV. Acts as a TCP <b>client</b>.
 */
public class RemoteControl {

  private static final String SERVER_HOST = "localhost";
  private Socket socket;
  private PrintWriter socketWriter;
  private BufferedReader socketReader;

  /**
   * Run the remote control.
   *
   * @param args Command line arguments. Not used.
   */
  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();
    remoteControl.run();
  }

  private void run() {
    try {
      this.socket = new Socket("localhost", PORT_NUMBER);
      this.socketWriter = new PrintWriter(this.socket.getOutputStream(), true);
      this.socketReader = new BufferedReader(
          new InputStreamReader(this.socket.getInputStream()));
      sendCommandToServer("c");
      sendCommandToServer("1");
      sendCommandToServer("c");
      sendCommandToServer("s13");

    } catch (IOException e) {
      System.err.println("Could not establish connection to the server: " + e.getMessage());
    }
  }

  private void run1() {
    if (connect()) {
      sendAndReceive("c");
      disconnect();
    }
    System.out.println("Exiting...");
  }

  private void sendAndReceive(String command) {
    if (sendToServer(command)) {
      String response = receiveOneLineFromServer();
      if (response != null) {
        System.out.println("Server's response: " + response);
      }
    }
  }

  /**
   * Establish a connection to a TCP server.
   *
   * @return true on success, false on error.
   */
  private boolean connect() {
    boolean success = false;
    try {
      this.socket = new Socket(SERVER_HOST, PORT_NUMBER);
      this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.socketWriter = new PrintWriter(socket.getOutputStream(), true);
      System.out.println("Connection established");
      success = true;
    } catch (IOException e) {
      System.err.println("Could not connect to the server: " + e.getMessage());
    }
    return success;
  }

  /**
   * Send a message to the TCP server. We assume that the connection is already established.
   *
   * @param message the message to send.
   * @return true when the message is successfully sent, false on error.
   */
  private boolean sendToServer(String message) {
    boolean sent = false;
    try {
      socketWriter.println(message);
      sent = true;
    } catch (Exception e) {
      System.err.println("Error while sending the message: " + e.getMessage());
    }
    return sent;
  }

  /**
   * Receive one line of text from the server (the TCP socket).
   *
   * @return the received line or null on error.
   */
  private String receiveOneLineFromServer() {
    String response = null;
    try {
      response = socketReader.readLine();
    } catch (IOException e) {
      System.err.println("Error while receiving data from the server: " + e.getMessage());
    }
    return response;
  }

  /**
   * Close the TCO connection.
   */
  private void disconnect() {
    try {
      if (socket != null) {
        socket.close();
        System.out.println("Socket closed");
      } else {
        System.err.println("Can't close a socket which has not been open");
      }
    } catch (IOException e) {
      System.err.println("Could not close the socket" + e.getMessage());
    }
  }

  /**
   * Sends a single command to the server.
   *
   * @param command the command to send.
   * @throws IOException throws exception when the message failed to send.
   */
  private void sendCommandToServer(String command) throws IOException {
    this.socketWriter.println(command);
    String serverResponse = this.socketReader.readLine();
    System.out.println("Server's response: " + serverResponse);
  }
}
