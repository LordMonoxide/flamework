package flamework.http;

public abstract class Response {
  public int    status;
  public String content;
  
  public abstract void send(Request request);
}