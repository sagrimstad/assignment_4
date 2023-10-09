package no.ntnu.message;

import no.ntnu.SmartTvState;

public class StateMessage implements Message{
  private final SmartTvState state;

  public StateMessage(SmartTvState state) {
    this.state = state;
  }

  public SmartTvState getState() {
    return this.state;
  }

}
