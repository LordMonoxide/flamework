package flamework.http;

import java.util.HashMap;
import java.util.Map;

public abstract class Response {
  public int    status;
  public String content;
  public final Map<String, String> headers = new HashMap<>();
  
  public abstract void send(Request request);
}