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
    
    public Events onError  (ErrorEvent   callback) { _error  .add(callback); return this; }
    public Events onBind   (BindEvent    callback) { _bind   .add(callback); return this; }
    public Events onClose  (CloseEvent   callback) { _close  .add(callback); return this; }
    public Events onRequest(RequestEvent callback) { _request.add(callback); return this; }
    
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
    
    public void raiseBind(boolean success) {
      for(BindEvent callback : _bind) {
        callback.execute(success);
      }
    }
    
    public void raiseClose(boolean success) {
      for(CloseEvent callback : _close) {
        callback.execute(success);
      }
    }
    
    public interface Event        { public void execute(boolean success); }
    public interface ErrorEvent   { public void execute(Throwable cause); }
    public interface BindEvent    { public void execute(boolean success); }
    public interface CloseEvent   { public void execute(boolean success); }
    public interface RequestEvent { public void execute(Request request); }
  }
}