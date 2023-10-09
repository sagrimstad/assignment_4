package no.ntnu.run;

import no.ntnu.SmartTv;
import no.ntnu.communication.SmartTvServer;

public class SmartTvRunner {

  /**
   * Entrypoint for the application-
   *
   * @param args Command line arguments, not used.
   */
  public static void main(String[] args) {
    //TODO:
    //SmartTv smartTv = new SmartTv();
    SmartTvServer server = new SmartTvServer();
    //server.run();
  }

}
