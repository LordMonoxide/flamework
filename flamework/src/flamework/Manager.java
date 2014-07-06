package flamework;

import bootstrap.Loader;

public class Manager {
  public static void main(String[] args) throws Exception {
    Loader loader = new Loader(Loader.class.getClassLoader());
    Manager manager = loader.create("flamework.Manager");
    manager.start();
  }
  
  public void start() {
    
  }
}