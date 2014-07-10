package flamework.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import flamework.Settings;

public class MySQL implements DatabaseInterface {
  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private final Connection _connection;
  
  public MySQL(String host, String database, String username, String password) throws SQLException {
    _connection = createConnection(host, database, username, password);
  }
  
  public MySQL(Settings settings) throws SQLException {
    this(settings.database.host, settings.database.database, settings.database.username, settings.database.password);
  }

  @Override public Connection createConnection(String host, String database, String username, String password) throws SQLException {
    return DriverManager.getConnection(
      "jdbc:mysql://" + host + '/' + database + "?user=" + username + "&password=" + password
    );
  }
  
  @Override public Table table(String name) {
    return new Table(this, name);
  }
  
  @Override public ResultSet query(String sql) throws SQLException {
    Statement s = _connection.createStatement();
    return s.executeQuery(sql);
  }
}