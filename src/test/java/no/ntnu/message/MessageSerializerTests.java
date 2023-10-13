package no.ntnu.message;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


import org.junit.Test;

public class MessageSerializerTests {

  @Test
  public void testChannelCountCommand() {
    Message m = MessageSerializer.fromString("c");
    assertNotNull(m);
    assertTrue(m instanceof ChannelCountCommand);
  }

  @Test
  public void testTurnOnCommand() {
    Message m = MessageSerializer.fromString("1");
    assertNotNull(m);
    assertTrue(m instanceof TurnOnCommand);
  }
}