package flamework.http;

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
}