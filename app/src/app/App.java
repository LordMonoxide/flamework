package app;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.views.Login;
import flamework.routing.Route;
import flamework.sql.Database;
import flamework.sql.MySQL;
import bootstrap.Loader;

public class App {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
    Loader loader = new Loader(Loader.class.getClassLoader());
    
    loader.override("flamework.http.Server", "flamework.http.netty.Server");
    loader.override("flamework.sql.Database", "flamework.sql.MySQL");
    loader.create("app.App");
  }
  
  public App() {
    flamework.App app = new flamework.App();
    
    app.settings.database.host = "localhost";
    app.settings.database.database = "juxxi";
    app.settings.database.username = "root";
    app.settings.database.password = "";
    
    app.settings.views.directory = "../views";
    
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
      
    });
    
    try {
      Database db = new Database(app.settings);
      
      ResultSet user = db.table("users").select().where("email", "corey@narwhunderful.com").get();
      while(user.next()) {
        System.out.println(user.getString("email"));
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }
    
    Route login = app.router.get("/login", request -> {
      System.out.println("LOGIN --------------------------------------");
      return app.responder.view(new Login());
    });
    
    Route home = app.router.get("/", request -> {
      System.out.println("HOME --------------------------------------");
      //return app.responder.respond("Home");
      return app.responder.redirect(login);
    });
    
    app.listen(4000);
  }
}