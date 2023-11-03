package no.ntnu.tv;

/**
 * The TvLogic class represents the logic of the smart TV.
 *
 * @author Group 2
 * @version v1.0 (2023.11.02)
 */
public class TvLogic {

  private static final String ERR_MUST_TURN_ON = "Must turn ON the smart TV first";
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;

  /**
   * Constructs an instance of the TvLogic class.
   *
   * <p>A specified number of channels cannot be negative or 0.</p>
   *
   * @param numberOfChannels A specified number of channels
   * @throws IllegalArgumentException If a specified number of channels is negative or 0
   */
  public TvLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("A specified number of channels is negative or 0 in "
          + "the constructor");
    }
    this.numberOfChannels = numberOfChannels;
    this.isTvOn = false;
    this.currentChannel = 1;
  }

  /**
   * Turns ON the smart TV.
   */
  public void turnOn() {
    this.isTvOn = true;
  }

  /**
   * Turn OFF the smart TV.
   */
  public void turnOff() {
    this.isTvOn = false;
  }

  /**
   * Retruns true when the smart TV is ON or false when it is OFF.
   *
   * @return True when the smart TV is ON or false when it is OFF
   */
  public boolean isTvOn() {
    return this.isTvOn;
  }

  /**
   * Returns the total number of channels on the smart TV.
   *
   * <p>The smart TV cannot be OFF.</p>
   *
   * @return The total number of channels on the smart TV
   * @throws IllegalStateException If the smart TV is OFF
   */
  public int getNumberOfChannels() {
    if (!this.isTvOn) {
      throw new IllegalStateException(ERR_MUST_TURN_ON + " in the getNumberOfChannels method");
    }
    return this.numberOfChannels;
  }

  /**
   * Returns the current channel on the smart TV.
   *
   * <p>The smart TV cannot be OFF.</p>
   *
   * @return The current channel on the smart TV
   * @throws IllegalStateException If the smart TV is OFF
   */
  public int getCurrentChannel() {
    if (!this.isTvOn) {
      throw new IllegalStateException(ERR_MUST_TURN_ON + " in the getCurrentChannel method");
    }
    return this.currentChannel;
  }

  /**
   * Sets the current channel on the smart TV to a specified channel.
   *
   * <p>A specified channel cannot be negative or 0 or greater than the number of channels on the
   * smart TV.</p>
   *
   * @param channel A specified channel
   * @throws IllegalStateException    If the smart TV is OFF
   * @throws IllegalArgumentException If a specified channel is negative or 0 or greater than the
   *                                  number of channels on the smart TV
   */
  public void setChannel(int channel) throws IllegalStateException, IllegalArgumentException {
    if (!this.isTvOn) {
      throw new IllegalStateException(ERR_MUST_TURN_ON + " in the setChannel method");
    }
    if (channel <= 0) {
      throw new IllegalArgumentException("A specified channel is negative or 0 in the "
          + "setChannel method");
    }
    if (channel > this.numberOfChannels) {
      throw new IllegalArgumentException("A specified channel is greater than the number of "
          + "channels on the smart TV in the setChannel method");
    }
    this.currentChannel = channel;
  }
}