package no.ntnu.run;

import no.ntnu.TvLogic;

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
    no.ntnu.TvServer server = new no.ntnu.TvServer(logic);
    server.startServer();
  }
}