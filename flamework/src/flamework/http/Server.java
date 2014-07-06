package flamework.http;

import java.util.concurrent.Future;

public class Server implements ServerInterface {
  public Server() {
    System.err.println(this + " should not have been instanciated!");
  }
  
  @Override public Future<?> listen(int port) {
    return null;
  }
}