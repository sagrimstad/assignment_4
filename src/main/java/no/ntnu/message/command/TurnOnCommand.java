package no.ntnu.message.command;

import no.ntnu.message.Message;
import no.ntnu.message.TvStateMessage;
import no.ntnu.tv.TvLogic;

/**
 * A command requesting to turn on the TV.
 */
public class TurnOnCommand extends Command {
  @Override
  public Message execute(TvLogic logic) {
    logic.turnOn();
    return new TvStateMessage(logic.isTvOn());
  }
}