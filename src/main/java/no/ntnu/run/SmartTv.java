package no.ntnu.run;

import no.ntnu.TvLogic;
import no.ntnu.communication.TvServer;

/**
 * Run the whole Smart TV, including the TCP socket communication.
 */
public class SmartTv {
  /**
   * Run the Smart TV, including the server.
   *
   * @param args Command line arguments, not used
   */
  public static void main(String[] args) {
    TvLogic logic = new TvLogic(13);
    TvServer server = new TvServer(logic);
    server.startServer();
  }
}