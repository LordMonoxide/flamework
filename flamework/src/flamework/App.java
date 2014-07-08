package flamework;

import bootstrap.InitializerInterface;
import bootstrap.Loader;
import flamework.http.Server;
import flamework.http.ServerInterface;
import flamework.routing.Router;

public class App implements InitializerInterface {
  public static void main(String[] args) throws Exception {
    Loader loader = new Loader(Loader.class.getClassLoader());
    
    loader.override("flamework.http.Server", "flamework.http.netty.Server");
    
    InitializerInterface app = loader.create("flamework.Manager");
    app.start();
  }
  
  private ServerInterface _server;
  public final Router router = new Router();
  
  public void start() {
    _server = new Server();
    
    _server.events().onError(cause -> {
      System.err.println("Server error:\n" + cause);
    }).onBind(success -> {
      if(success) {
        System.out.println("Server bound.");
      } else {
        System.err.println("Server bind failed.");
      }
    }).onClose(success -> {
      if(success) {
        System.out.println("Server closed.");
      } else {
        System.err.println("Server close failed.");
      }
    }).onRequest(request -> {
      System.out.println("REQUEST:");
      System.out.println("HOST: " + request.host);
      System.out.println("ROUTE: " + request.route);
      
      System.out.println("HEADERS:");
      
      request.headers.forEach(entry -> {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      });
      
      System.out.println();
      System.out.println("PARAMS:");
      
      request.params.forEach(entry -> {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      });
      
      System.out.println();
      System.out.println("CONTENT:");
      System.out.println(request.content);
    });
    
    _server.listen(4000);
  }
}