package flamework.http;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface ServerInterface {
  public Events events();
  public void listen(int port);
  public void close();
  public void destroy();
  
  public final class Events {
    private final ConcurrentLinkedQueue<ErrorEvent  > _error   = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<BindEvent   > _bind    = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<CloseEvent  > _close   = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<RequestEvent> _request = new ConcurrentLinkedQueue<>();
    
    public void onError  (ErrorEvent   callback) { _error  .add(callback); }
    public void onBind   (BindEvent    callback) { _bind   .add(callback); }
    public void onClose  (CloseEvent   callback) { _close  .add(callback); }
    public void onRequest(RequestEvent callback) { _request.add(callback); }
    
    public void raiseError(Throwable cause) {
      for(ErrorEvent callback : _error) {
        callback.execute(cause);
      }
    }
    
    public void raiseRequest(Request request) {
      for(RequestEvent callback : _request) {
        callback.execute(request);
      }
    }
    
    public void raiseBind() {
      for(BindEvent callback : _bind) {
        callback.execute();
      }
    }
    
    public void raiseClose() {
      for(CloseEvent callback : _close) {
        callback.execute();
      }
    }
    
    public interface Event        { public void execute(boolean success); }
    public interface ErrorEvent   { public void execute(Throwable cause); }
    public interface BindEvent    { public void execute(); }
    public interface CloseEvent   { public void execute(); }
    public interface RequestEvent { public void execute(Request request); }
  }
}