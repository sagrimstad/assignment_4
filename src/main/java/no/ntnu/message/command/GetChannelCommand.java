package no.ntnu.message.command;


import no.ntnu.message.CurrentChannelMessage;
import no.ntnu.message.ErrorMessage;
import no.ntnu.message.Message;
import no.ntnu.tv.TvLogic;

public class GetChannelCommand extends Command {

  /**
   * A command requesting to know the current channel of a TV.
   */
  @Override
  public Message execute(TvLogic logic) {
    Message response;
    try {
      int channel = logic.getCurrentChannel();
      response = new CurrentChannelMessage(channel);
    } catch (Exception e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }
}
