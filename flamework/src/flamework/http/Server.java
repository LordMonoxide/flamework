package flamework.http;

import flamework.http.ServerInterface.Events.Event;

public class Server implements ServerInterface {
  public Server() {
    System.err.println(this + " should not have been instanciated!");
  }
  
  @Override public void listen(int port, Events.Event callback) {
    
  }
  
  @Override public void close(Event callback) {
    
  }
  
  @Override public void destroy() {
    
  }
}