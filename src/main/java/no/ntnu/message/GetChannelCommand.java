package no.ntnu.message;


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
