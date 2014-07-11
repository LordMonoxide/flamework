package flamework.sql;

public interface ModelInterface<ModelType, ID> {
  ModelType get(ID id);
}