package no.ntnu.message.command;

import no.ntnu.message.Message;
import no.ntnu.message.TvStateMessage;
import no.ntnu.tv.TvLogic;

/**
 * A command requesting to turn off the TV.
 */
public class TurnOffCommand extends Command {

  @Override
  public Message execute(TvLogic logic) {
    logic.turnOff();
    return new TvStateMessage(logic.isTvOn());
  }
}
