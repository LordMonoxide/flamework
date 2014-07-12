package flamework.database;

import flamework.App;

public abstract class Model<ModelType, ID> implements ModelInterface<ModelType, ID> {
  protected App _app;
  
  public Model(App app) {
    _app = app;
  }
  
  @Override public Query createQuery(DatabaseInterface database) {
    return database.table(table()).select();
  }
  
  @Override public Query createQuery(ID id, DatabaseInterface database) {
    return createQuery(database).where(key(), id);
  }
}