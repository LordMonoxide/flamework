package flamework.database;

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
  
  private final Settings _settings;
  
  public MySQL(Settings settings) throws SQLException {
    _settings = settings;
  }
  
  private Connection createConnection() throws SQLException {
    return DriverManager.getConnection(
      "jdbc:mysql://" + _settings.database.host + '/' + _settings.database.database + "?user=" + _settings.database.username + "&password=" + _settings.database.password
    );
  }
  
  @Override public void transact(DatabaseTransactionCallback callback) throws SQLException {
    Connection connection = createConnection();
    
    callback.execute(new DatabaseTransaction() {
      @Override public Table table(String name) {
        return new Table(this, name);
      }
      
      @Override public ResultSet query(String sql) throws SQLException {
        Statement s = connection.createStatement();
        return s.executeQuery(sql);
      }
    });
    
    connection.close();
  }
}