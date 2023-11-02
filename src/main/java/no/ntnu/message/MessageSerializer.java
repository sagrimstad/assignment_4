package no.ntnu.message;

import no.ntnu.message.command.ChannelCountCommand;
import no.ntnu.message.command.GetChannelCommand;
import no.ntnu.message.command.SetChannelCommand;
import no.ntnu.message.command.TurnOffCommand;
import no.ntnu.message.command.TurnOnCommand;

/**
 * Serializes messages to protocol-defined strings and vice versa.
 */
public class MessageSerializer {

  public static final String CHANNEL_COUNT_COMMAND = "c";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String GET_CHANNEL_COMMAND = "g";
  public static final String SET_CHANNEL_COMMAND = "s";
  public static final String CHANNEL_COUNT_MESSAGE = "N";
  public static final String ERROR_MESSAGE = "e";
  public static final String CURRENT_CHANNEL_MESSAGE = "C";
  public static final String TV_STATE_ON_MESSAGE = "TVON";
  public static final String TV_STATE_OFF_MESSAGE = "TVoff";

  /**
   * Not allowed to instantiate this utility class.
   */
  private MessageSerializer() {
    // Intentionally left blank.
  }

  /**
   * Create message from a string, according to the communication protocol.
   *
   * @param s The string sent over the communication channel
   * @return The logical message, as interpreted according to the protocol
   */
  public static Message fromString(String s) {
    Message m = null;
    if (s != null) {
      switch (s) {
        case CHANNEL_COUNT_COMMAND -> m = new ChannelCountCommand();
        case TURN_ON_COMMAND -> m = new TurnOnCommand();
        case TURN_OFF_COMMAND -> m = new TurnOffCommand();
        case GET_CHANNEL_COMMAND -> m = new GetChannelCommand();
        case TV_STATE_ON_MESSAGE -> m = new TvStateMessage(true);
        case TV_STATE_OFF_MESSAGE -> m = new TvStateMessage(false);
        default -> {
          if (s.length() > 1) {
            m = parseParametrizedMessage(s);
          }
        }
      }
    }
    return m;
  }

  private static Message parseParametrizedMessage(String s) {
    Message m = null;
    String parameter = s.substring(1);
    if (s.startsWith(SET_CHANNEL_COMMAND)) {
      Integer desiredChannel = parseInteger(parameter);
      if (desiredChannel != null) {
        m = new SetChannelCommand(desiredChannel);
      }
    } else if (s.startsWith(ERROR_MESSAGE)) {
      m = new ErrorMessage(parameter);
    } else if (s.startsWith(CURRENT_CHANNEL_MESSAGE)) {
      Integer currentChannel = parseInteger(parameter);
      if (currentChannel != null) {
        m = new CurrentChannelMessage(currentChannel);
      }
    } else if (s.startsWith(CHANNEL_COUNT_MESSAGE)) {
      Integer channelCount = parseInteger(parameter);
      if (channelCount != null) {
        m = new ChannelCountMessage(channelCount);
      }
    }
    return m;
  }

  private static Integer parseInteger(String s) {
    Integer i = null;
    try {
      i = Integer.valueOf(s);
    } catch (NumberFormatException e) {
      System.err.println("Could not parse integer <" + s + ">");
    }
    return i;
  }

  /**
   * Convert a message to a serialized string.
   *
   * @param m The message to translate
   * @return String representation of the message
   */
  public static String toString(Message m) {
    String s = null;
    if (m instanceof TurnOffCommand) {
      s = TURN_OFF_COMMAND;
    } else if (m instanceof TurnOnCommand) {
      s = TURN_ON_COMMAND;
    } else if (m instanceof ChannelCountCommand) {
      s = CHANNEL_COUNT_COMMAND;
    } else if (m instanceof GetChannelCommand) {
      s = GET_CHANNEL_COMMAND;
    } else if (m instanceof ChannelCountMessage channelCountMessage) {
      s = CHANNEL_COUNT_MESSAGE + channelCountMessage.getChannelCount();
    } else if (m instanceof CurrentChannelMessage currentChannelMessage) {
      s = CURRENT_CHANNEL_MESSAGE + currentChannelMessage.getChannel();
    } else if (m instanceof ErrorMessage errorMessage) {
      s = ERROR_MESSAGE + errorMessage.getMessage();
    } else if (m instanceof TvStateMessage tvStateMessage) {
      s = tvStateMessage.isOn() ? TV_STATE_ON_MESSAGE : TV_STATE_OFF_MESSAGE;
    } else if (m instanceof SetChannelCommand setChannelCommand) {
      s = SET_CHANNEL_COMMAND + setChannelCommand.getChannel();
    }
    return s;
  }
}