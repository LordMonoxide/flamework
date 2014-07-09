package app;

import java.io.IOException;

import flamework.routing.Route;
import bootstrap.Loader;

public class App {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
    Loader loader = new Loader(Loader.class.getClassLoader());
    
    loader.override("flamework.http.Server", "flamework.http.netty.Server");
    loader.create("app.App");
  }
  
  public App() {
    flamework.App app = new flamework.App();
    
    app.events.onError(cause -> {
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
      /*System.out.println("REQUEST:");
      System.out.println("HOST: " + request.host);
      System.out.println("ROUTE: " + request.uri);
      
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
      System.out.println(request.content);*/
    });
    
    Route home = app.router.get("/", request -> {
      System.out.println("HOME --------------------------------------");
      return app.responder.respond("Home");
    });
    
    Route login = app.router.get("/login", request -> {
      System.out.println("LOGIN --------------------------------------");
      return app.responder.respond("Login");
    });
    
    app.listen(4000);
  }
}