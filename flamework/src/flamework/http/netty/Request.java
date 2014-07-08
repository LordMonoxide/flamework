package flamework.http.netty;

import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

public class Request extends flamework.http.Request {
  protected Request(HttpRequest request, Map<String, List<String>> params, String content) {
    super(HttpHeaders.getHost(request, "unknown"), request.uri(), new Request.Headers(adder -> {
      for(Map.Entry<String, String> e : request.headers()) {
        adder.add(e.getKey(), e.getValue());
      }
    }), new Request.Params(adder -> {
      for(Map.Entry<String, List<String>> e : params.entrySet()) {
        adder.add(e.getKey(), e.getValue());
      }
    }), content);
  }
}