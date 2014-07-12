package app.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import flamework.App;
import flamework.database.Model;

public class User extends Model<User, Integer> {
  private int _id;
  private String _email;
  private String _password;
  
  public User(App app) {
    super(app);
  }
  
  @Override public String table() {
    return "users";
  }
  
  @Override public String key() {
    return "id";
  }
  
  @Override public void fromResultSet(ResultSet r) throws SQLException {
    _id       = r.getInt("id");
    _email    = r.getString("email");
    _password = r.getString("password");
  }
  
  public int id() {
    return _id;
  }
  
  public String email() {
    return _email;
  }
  
  public String password() {
    return _password;
  }
}