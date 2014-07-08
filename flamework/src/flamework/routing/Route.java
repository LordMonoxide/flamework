package flamework.routing;

import flamework.http.Request;

public class Route {
  public final Request.Method    method;
  public final String            uri;
  public final RoutableInterface destination;
  
  Route(Request.Method method, String uri, RoutableInterface destination) {
    this.method      = method;
    this.uri         = uri;
    this.destination = destination;
  }
}