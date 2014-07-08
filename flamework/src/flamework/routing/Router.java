package flamework.routing;

import java.util.HashMap;
import java.util.Map;

import flamework.http.Request;

public class Router {
  private Map<String, Route> _get    = new HashMap<>();
  private Map<String, Route> _post   = new HashMap<>();
  private Map<String, Route> _put    = new HashMap<>();
  private Map<String, Route> _delete = new HashMap<>();
  
  public Route route(Request.Method method, String uri, RoutableInterface destination) {
    Route route = new Route(method, uri, destination);
    
    switch(method) {
      case GET:    _get   .put(uri, route); break;
      case POST:   _post  .put(uri, route); break;
      case PUT:    _put   .put(uri, route); break;
      case DELETE: _delete.put(uri, route); break;
    }
    
    return route;
  }
  
  public Route get(String uri, RoutableInterface destination) {
    return route(Request.Method.GET, uri, destination);
  }
  
  public Route post(String uri, RoutableInterface destination) {
    return route(Request.Method.POST, uri, destination);
  }
  
  public Route put(String uri, RoutableInterface destination) {
    return route(Request.Method.PUT, uri, destination);
  }
  
  public Route delete(String uri, RoutableInterface destination) {
    return route(Request.Method.DELETE, uri, destination);
  }
}