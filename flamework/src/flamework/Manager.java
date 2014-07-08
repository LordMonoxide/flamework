package flamework;

import bootstrap.InitializerInterface;
import bootstrap.Loader;
import flamework.http.Server;
import flamework.http.ServerInterface;

public class Manager implements InitializerInterface {
  public static void main(String[] args) throws Exception {
    Loader loader = new Loader(Loader.class.getClassLoader());
    
    loader.addMapping("flamework.http.Server", "flamework.http.netty.Server");
    
    InitializerInterface manager = loader.create("flamework.Manager");
    manager.start();
  }
  
  private ServerInterface _server;
  
  public void start() {
    _server = new Server();
    
    _server.events().onError(cause -> {
      System.err.println("Server error:\n" + cause);
    });
    
    _server.events().onRequest(request -> {
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
    
    _server.listen(4000, success -> {
      System.out.println("Listen: " + success);
      
      /*_server.close(closeSuccess -> {
        System.out.println("Closed: " + closeSuccess);
      });*/
    });
  }
}