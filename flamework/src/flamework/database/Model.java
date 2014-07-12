package flamework.database;

import flamework.App;

public abstract class Model<ModelType, ID> implements ModelInterface<ModelType, ID> {
  protected App _app;
  
  public Model(App app) {
    _app = app;
  }
}