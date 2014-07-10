package flamework.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query {
  private Database _database;
  private String _query;
  private List<Where> _where = new ArrayList<>();
  
  Query(Database database, String query) {
    _database = database;
    _query = query;
  }
  
  public Query where(String column, String operator, String value) {
    _where.add(new Where(column, operator, value));
    return this;
  }
  
  public Query where(String column, String value) {
    _where.add(new Where(column, value));
    return this;
  }
  
  public String build() {
    StringBuilder builder = new StringBuilder();
    builder.append(_query);
    
    if(_where.size() != 0) {
      builder.append(" WHERE ");
      
      for(int i = 0; i < _where.size(); i++) {
        Where where = _where.get(i);
        
        if(i != 0) {
          builder.append(" AND ");
        }
        
        builder.append(where.column).append(where.operator).append('\'').append(where.value).append('\'');
      }
    }
    
    return builder.toString();
  }
  
  @Override public String toString() {
    return build();
  }
  
  public ResultSet get() throws SQLException {
    return _database.query(build());
  }
  
  private class Where {
    private final String column;
    private final String operator;
    private final String value;
    
    private Where(String column, String operator, String value) {
      this.column   = column;
      this.operator = operator;
      this.value    = value;
    }
    
    private Where(String column, String value) {
      this(column, "=", value);
    }
  }
}