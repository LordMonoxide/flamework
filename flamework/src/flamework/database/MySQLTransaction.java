package flamework.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLTransaction implements DatabaseInterface.DatabaseTransaction {
  private final Connection _connection;
  
  MySQLTransaction(Connection connection) {
    _connection = connection;
  }
  
  @Override public Table table(String name) {
    return new Table(this, name);
  }
  
  @Override public ResultSet query(String sql) throws SQLException {
    Statement s = _connection.createStatement();
    return s.executeQuery(sql);
  }
}