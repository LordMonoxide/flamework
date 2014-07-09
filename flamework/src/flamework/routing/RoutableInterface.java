package flamework.routing;

import flamework.http.Request;
import flamework.http.Response;

public interface RoutableInterface {
  public Response execute(Request request);
}