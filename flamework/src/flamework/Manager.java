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
    _server.listen(4000, (success) -> {
      System.out.println(success);
    });
  }
}