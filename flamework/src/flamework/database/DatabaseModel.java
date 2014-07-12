package flamework.database;

import flamework.App;

public abstract class DatabaseModel<ModelType, ID> implements ModelInterface<ModelType, ID> {
  protected App _app;
  
  public DatabaseModel(App app) {
    _app = app;
  }
}