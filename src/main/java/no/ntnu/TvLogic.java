package no.ntnu;

/**
 * A smart TV. Acts as a TCP <b>server</b>.
 */
public class TvLogic {

  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;


  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has
   */
  public TvLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }

    this.numberOfChannels = numberOfChannels;
    this.isTvOn = false;
    this.currentChannel = 1;
  }

  /**
   * Turns ON the TV.
   */
  public void turnOn() {
    this.isTvOn = true;
  }

  /**
   * Turns OFF the TV.
   */
  public void turnOff() {
    this.isTvOn = false;
  }

  /**
   * Check whether the TV is ON or OFF.
   *
   * @return True when the TV is ON, false when OFF.
   */
  public boolean isTvOn() {
    return this.isTvOn;
  }

  /**
   * Get the number of channels this TV has.
   *
   * @return The total number of channels
   * @throws IllegalStateException When the TV is OFF
   */
  public int getNumberOfChannels() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("Must turn on the TV first");
    }
    return numberOfChannels;
  }

  /**
   * Get the current channel of the TV.
   *
   * @return The current channel
   * @throws IllegalStateException When the TV is OFF
   */
  public int getCurrentChannel() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("Must turn on the TV first");
    }
    return currentChannel;
  }

  /**
   * Set the channel for the TV.
   *
   * @param channel The desired channel
   * @throws IllegalArgumentException When the channel number is invalid
   * @throws IllegalStateException    When the TV is OFF
   */
  public void setChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("Must turn on the TV first");
    }
    if (channel <= 0 || channel > numberOfChannels) {
      throw new IllegalArgumentException("Invalid channel number");
    }
    currentChannel = channel;
  }


  public SmartTvState getState() {
    //TODO
    //return state;
    return null;
  }

  public void setState(SmartTvState state) throws IllegalStateException {
    //TODO
  }

}
