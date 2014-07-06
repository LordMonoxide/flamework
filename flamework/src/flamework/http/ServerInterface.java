package flamework.http;

public interface ServerInterface {
  public void listen(int port, Events.Event callback);
  public void close(Events.Event callback);
  public void destroy();
  
  public static final class Events {
    private Events() { }
    
    public interface Event { public void event(boolean success); }
  }
}