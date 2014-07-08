package flamework.http;

public class Server implements ServerInterface {
  public Server() {
    System.err.println(this + " should not have been instanciated!");
  }
  
  @Override public Events events() {
    return null;
  }
  
  @Override public void listen(int port) {
    
  }
  
  @Override public void close() {
    
  }
  
  @Override public void destroy() {
    
  }
}