package flamework.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import flamework.Settings;

public class Database implements DatabaseInterface {
  public Database(String host, String database, String username, String password) throws SQLException {
    
  }
  
  public Database(Settings settings) throws SQLException {
    
  }
  
  public Connection createConnection(String host, String database, String username, String password) throws SQLException {
    return null;
  };
  
  public Table table(String name) {
    return null;
  }
  
  public ResultSet query(String sql) throws SQLException {
    return null;
  }
}