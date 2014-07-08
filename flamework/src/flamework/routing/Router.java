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
  
  public void get(String uri, RoutableInterface destination) {
    route(Request.Method.GET, uri, destination);
  }
  
  public void post(String uri, RoutableInterface destination) {
    route(Request.Method.POST, uri, destination);
  }
  
  public void put(String uri, RoutableInterface destination) {
    route(Request.Method.PUT, uri, destination);
  }
  
  public void delete(String uri, RoutableInterface destination) {
    route(Request.Method.DELETE, uri, destination);
  }
}