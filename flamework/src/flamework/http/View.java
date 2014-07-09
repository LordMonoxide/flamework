package flamework.http;

public abstract class View {
  private StringBuilder _builder;
  
  public abstract String getTitle();
  
  public String build() {
    _builder = new StringBuilder();
    
    addDoctype("html");
    
    startHTML(() -> {
      startHead(() -> {
        addCharset("utf-8");
        addTitle();
      });
      
      startBody(() -> {
        
      });
    });
    
    return _builder.toString();
  }
  
  private void addDoctype(String type) {
    _builder.append("<!DOCTYPE ").append(type).append('>');
  }
  
  private void startHTML(Callback callback) {
    _builder.append("<html>");
    callback.execute();
    _builder.append("</html>");
  }
  
  private void startHead(Callback callback) {
    _builder.append("<head>");
    callback.execute();
    _builder.append("</head>");
  }
  
  private void startBody(Callback callback) {
    _builder.append("<body>");
    callback.execute();
    _builder.append("</body>");
  }
  
  private void addCharset(String charset) {
    _builder.append("<meta charset=\"").append(charset).append("\" />");
  }
  
  private void addTitle() {
    _builder.append("<title>").append(getTitle()).append("</title>");
  }
  
  private interface Callback {
    public void execute();
  }
}