package flamework.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import flamework.Settings;

public class MySQL extends Database {
  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public MySQL(String host, String database, String username, String password) throws SQLException {
    super(host, database, username, password);
  }
  
  public MySQL(Settings settings) throws SQLException {
    super(settings);
  }

  @Override protected Connection createConnection(String host, String database, String username, String password) throws SQLException {
    return DriverManager.getConnection(
      "jdbc:mysql://" + host + '/' + database + "?user=" + username + "&password=" + password
    );
  }
}