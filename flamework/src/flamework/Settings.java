package flamework;

public class Settings {
  public final Database database = new Database();
  public final Views views = new Views();
  
  public class Database {
    public String host;
    public String database;
    public String username;
    public String password;
  }
  
  public class Views {
    public String directory;
  }
}