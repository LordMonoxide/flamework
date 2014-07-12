package flamework.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseInterface {
  void transact(DatabaseTransactionCallback callback) throws SQLException;
  
  public interface DatabaseTransaction {
    Table table(String name);
    ResultSet query(String sql) throws SQLException;
  }
  
  public interface DatabaseTransactionCallback {
    public void execute(DatabaseTransaction transaction) throws SQLException;
  }
}