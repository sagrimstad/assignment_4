package no.ntnu.message.command;

import no.ntnu.message.CurrentChannelMessage;
import no.ntnu.message.ErrorMessage;
import no.ntnu.message.Message;
import no.ntnu.tv.TvLogic;

/**
 * A message requesting the channel is switched on a Tv.
 */
public class SetChannelCommand extends Command {
  private final int channel;

  /**
   * Create a new command for setting channel for a Tv.
   *
   * @param channel The desired channel to set.
   */
  public SetChannelCommand(int channel) {
    this.channel = channel;
  }

  @Override
  public Message execute(TvLogic logic) {
    try {
      logic.setChannel(channel);
      return new CurrentChannelMessage(channel);
    } catch (Exception e) {
      return new ErrorMessage(e.getMessage());
    }
  }

  /**
   * Get the desired channel
   *
   * @return The channel which this command should set.
   */
  public int getChannel() {
    return channel;
  }

}
