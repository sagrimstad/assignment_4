package no.ntnu.message.command;

import no.ntnu.message.ChannelCountMessage;
import no.ntnu.message.ErrorMessage;
import no.ntnu.message.Message;
import no.ntnu.tv.TvLogic;

/**
 * A command asking for the number of the channels.
 */
public class ChannelCountCommand extends Command {

  @Override
  public Message execute(TvLogic logic) {
    Message response;
    try {
      int channelCount = logic.getNumberOfChannels();
      response = new ChannelCountMessage(channelCount);
    } catch (IllegalStateException e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }
}