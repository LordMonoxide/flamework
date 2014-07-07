package flamework.http;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface ServerInterface {
  public Events events();
  public void listen(int port, Events.Event callback);
  public void close(Events.Event callback);
  public void destroy();
  
  public final class Events {
    private final ConcurrentLinkedQueue<Error> _error = new ConcurrentLinkedQueue<>();
    
    public void onError(Error callback) { _error.add(callback); }
    
    public void raiseError(Throwable cause) {
      for(Error callback : _error) {
        callback.execute(cause);
      }
    }
    
    public interface Error { public void execute(Throwable cause); }
    public interface Event { public void execute(boolean success); }
  }
}