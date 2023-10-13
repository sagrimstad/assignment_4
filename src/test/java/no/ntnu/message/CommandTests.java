package no.ntnu.message;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import no.ntnu.ErrorMessage;
import no.ntnu.TvLogic;
import org.junit.Test;

public class CommandTests {
  private final static int CHANNEL_COUNT = 5;

  @Test
  public void testChannelCountWhenTvIsOff() {
    ChannelCountCommand c = new ChannelCountCommand();
    TvLogic logic = new TvLogic(CHANNEL_COUNT);
    Message response = c.execute(logic);
    assertTrue(response instanceof ErrorMessage);
  }

  @Test
  public void testChannelCount() {
    TvLogic logic = new TvLogic(CHANNEL_COUNT);
    logic.turnOn();
    ChannelCountCommand c = new ChannelCountCommand();
    Message response = c.execute(logic);
    assertTrue(response instanceof ChannelCountMessage);
    ChannelCountMessage channelCountMessage = (ChannelCountMessage) response;
    assertEquals(CHANNEL_COUNT, channelCountMessage.getChannelCount());
  }

  @Test
  public void testTurnOn() {
    TvLogic logic = new TvLogic(CHANNEL_COUNT);
    TurnOnCommand c = new TurnOnCommand();
    Message response = c.execute(logic);
    assertTrue(response instanceof OkMessage);
    assertTrue(logic.isTvOn());
  }

}