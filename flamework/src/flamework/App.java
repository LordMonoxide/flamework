package flamework;

import flamework.http.Server;
import flamework.http.ServerInterface;
import flamework.http.ServerInterface.Events;
import flamework.routing.Router;

public class App {
  private ServerInterface _server;
  public final Events events;
  public final Router router = new Router();
  
  public App() {
    _server = new Server();
    events  = _server.events();
  }
  
  public void listen(int port) {
    _server.listen(port);
  }
  
  public void close() {
    _server.close();
  }
}