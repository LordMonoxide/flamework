package flamework.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import flamework.Settings;

public abstract class Database {
  private final Connection _connection;
  
  public Database(String host, String database, String username, String password) throws SQLException {
    _connection = createConnection(host, database, username, password);
  }
  
  public Database(Settings settings) throws SQLException {
    this(settings.database.host, settings.database.database, settings.database.username, settings.database.password);
  }
  
  protected abstract Connection createConnection(String host, String database, String username, String password) throws SQLException;
  
  public Table table(String name) {
    return new Table(this, name);
  }
  
  public ResultSet query(String sql) throws SQLException {
    Statement s = _connection.createStatement();
    return s.executeQuery(sql);
  }
}