package flamework.routing;

import java.util.HashMap;
import java.util.Map;

import flamework.http.Request;

public class Router {
  private Map<String, RoutableInterface> _get    = new HashMap<>();
  private Map<String, RoutableInterface> _post   = new HashMap<>();
  private Map<String, RoutableInterface> _put    = new HashMap<>();
  private Map<String, RoutableInterface> _delete = new HashMap<>();
  
  public void route(Request.Method method, String route, RoutableInterface routable) {
    switch(method) {
      case GET:    _get   .put(route, routable); break;
      case POST:   _post  .put(route, routable); break;
      case PUT:    _put   .put(route, routable); break;
      case DELETE: _delete.put(route, routable); break;
    }
  }
  
  public void get(String route, RoutableInterface routable) {
    route(Request.Method.GET, route, routable);
  }
  
  public void post(String route, RoutableInterface routable) {
    route(Request.Method.POST, route, routable);
  }
  
  public void put(String route, RoutableInterface routable) {
    route(Request.Method.PUT, route, routable);
  }
  
  public void delete(String route, RoutableInterface routable) {
    route(Request.Method.DELETE, route, routable);
  }
}