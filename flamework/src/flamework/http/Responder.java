package flamework.http;

import io.netty.handler.codec.http.HttpHeaders;
import flamework.routing.Route;

public class Responder {
  private ServerInterface _server;
  
  public Responder(ServerInterface server) {
    _server = server;
  }
  
  public Response respond(String content) {
    Response response = _server.newResponse();
    response.status  = 200;
    response.content = content;
    return response;
  }
  
  public Response redirect(Route route) {
    Response response = _server.newResponse();
    response.status = 302;
    response.headers.put(HttpHeaders.Names.LOCATION, route.uri);
    return response;
  }
  
  public Response view(View view) {
    return respond(view.build());
  }
}