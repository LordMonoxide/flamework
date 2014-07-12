package flamework.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelInterface<ModelType, ID> {
  String table();
  String key();
  Query createQuery(DatabaseInterface database);
  Query createQuery(ID id, DatabaseInterface database);
  void fromResultSet(ResultSet r) throws SQLException;
}