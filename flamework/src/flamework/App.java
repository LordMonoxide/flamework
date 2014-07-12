package flamework;

import java.sql.SQLException;

import flamework.database.Database;
import flamework.http.Responder;
import flamework.http.Response;
import flamework.http.Server;
import flamework.http.ServerInterface;
import flamework.http.ServerInterface.Events;
import flamework.routing.Router;

public class App {
  private ServerInterface _server;
  public final Settings settings = new Settings();
  public final Events events;
  public final Router router = new Router();
  public final Responder responder;
  public final Database database;
  
  public App() throws SQLException {
    _server = new Server();
    events  = _server.events();
    responder = new Responder(_server);
    database = new Database(settings);
    
    events.onRequest(request -> {
      Response response = router.dispatch(request);
      response.send(request);
    });
  }
  
  public void listen(int port) {
    _server.listen(port);
  }
  
  public void close() {
    _server.close();
  }
}