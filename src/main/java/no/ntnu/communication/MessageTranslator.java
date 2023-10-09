package no.ntnu.communication;

import no.ntnu.SmartTv;
import no.ntnu.message.Message;

/**
 * Can translate Message objects to Strong and vice versa.
 */
public class MessageTranslator {
  private final SmartTv smartTv;

  /**
   * Creates a message translator.
   *
   * @param smartTv the smart tv logic.
   */
  public MessageTranslator(SmartTv smartTv) {
    this.smartTv = smartTv;
  }

  /**
   * Translate a string received over a communication channel (socket) to a Message object.
   *
   * @param messageString The raw message string
   * @return The corresponding message object or null if it is invalid
   */
  public Message fromString(String messageString) {
    //TODO
    Message message = null;
    return message;
  }

  /**
   * Translate a message to a string that can be sent over the communication channel (Socket).
   *
   * @param message The message to translate
   * @return A corresponding string or null if it can't be translated
   */
  public String toString(Message message) {
    //TODO
    String result = null;

    return result;
  }

}
