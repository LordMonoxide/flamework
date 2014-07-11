package flamework.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseInterface {
  Connection createConnection(String host, String database, String username, String password) throws SQLException;
  Table table(String name);
  ResultSet query(String sql) throws SQLException;
}