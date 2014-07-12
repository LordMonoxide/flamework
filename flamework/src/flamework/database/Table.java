package flamework.database;

public class Table {
  private DatabaseInterface.DatabaseTransaction _database;
  public final String name;
  
  Table(DatabaseInterface.DatabaseTransaction database, String name) {
    _database = database;
    this.name = name;
  }
  
  public Query select(String... columns) {
    StringBuilder b = new StringBuilder();
    
    b.append("SELECT ");
    
    if(columns.length != 0) {
      for(String s : columns) {
        if(b.length() != 0) {
          b.append(',');
        }
        
        b.append(s);
      }
    } else {
      b.append('*');
    }
    
    b.append(" FROM ");
    b.append(name);
    
    return new Query(_database, b.toString());
  }
}