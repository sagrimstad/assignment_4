package no.ntnu.message;

import no.ntnu.SmartTv;

public abstract class Command {
  protected final SmartTv smartTv;
  protected Command(SmartTv smartTv) {
    this.smartTv = smartTv;
  }

  /**
   * Execute the command - preform necessary logic calls.
   *
   * @return A message containing the response of the command execution.
   */
  public abstract Message execute();
}
