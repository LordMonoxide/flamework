package flamework.database;

import java.lang.reflect.InvocationTargetException;

import flamework.App;

public class Modeler {
  private App _app;
  
  public Modeler(App app) {
    _app = app;
  }
  
  public <ModelType, ID> Query get(Class<ModelType> c) {
    try {
      @SuppressWarnings("unchecked")
      Model<ModelType, ID> m = (Model<ModelType, ID>)c.getDeclaredConstructor(App.class).newInstance(_app);
      return m.createQuery(_app.database);
    } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  public <ModelType, ID> Query get(Class<ModelType> c, ID id) {
    try {
      @SuppressWarnings("unchecked")
      Model<ModelType, ID> m = (Model<ModelType, ID>)c.getDeclaredConstructor(App.class).newInstance(_app);
      return m.createQuery(id, _app.database);
    } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    
    return null;
  }
}