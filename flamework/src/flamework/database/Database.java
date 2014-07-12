package flamework.database;

import java.sql.SQLException;

import flamework.Settings;

public class Database implements DatabaseInterface {
  public Database(Settings settings) throws SQLException {
    
  }
  
  @Override public void transact(DatabaseTransactionCallback callback) {
    
  }
}