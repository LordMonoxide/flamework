package flamework.database;

public interface ModelInterface<ModelType, ID> {
  ModelType get(ID id);
}