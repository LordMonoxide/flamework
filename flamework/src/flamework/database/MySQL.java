package flamework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import flamework.Settings;

public class MySQL implements DatabaseInterface {
  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  private final Settings _settings;
  private Connection _connection;
  
  public MySQL(Settings settings) throws SQLException {
    _settings = settings;
    _connection = createConnection();
  }
  
  private Connection createConnection() throws SQLException {
    return DriverManager.getConnection(
      "jdbc:mysql://" + _settings.database.host + '/' + _settings.database.database + "?user=" + _settings.database.username + "&password=" + _settings.database.password
    );
  }
  
  @Override public Table table(String name) {
    return new Table(new MySQLTransaction(_connection), name);
  }
  
  @Override public void transact(DatabaseTransactionCallback callback) throws SQLException {
    //TODO: Add actual transaction stuff
    callback.execute(new MySQLTransaction(_connection));
  }
}