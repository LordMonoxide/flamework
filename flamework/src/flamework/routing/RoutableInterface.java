package flamework.routing;

import flamework.http.Request;

public interface RoutableInterface {
  public void execute(Request request);
}