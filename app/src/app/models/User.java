package app.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import flamework.App;
import flamework.database.Model;

public class User extends Model<User, String> {
  private int _id;
  private String _email;
  private String _password;
  
  public User(App app) {
    super(app);
  }
  
  @Override public User get(String id) {
    _app.database.transact(transaction -> {
      ResultSet r = transaction.table("users").select().where("email", id).get();
      if(r.next()) {
        fromResultSet(r);
      }
    });
    
    return null;
  }
  
  private void fromResultSet(ResultSet r) throws SQLException {
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