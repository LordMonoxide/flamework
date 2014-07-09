package flamework.http.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class Response extends flamework.http.Response {
  @Override public void send(flamework.http.Request request) {
    Request r = (Request)request;
    
    boolean keepAlive = HttpHeaders.isKeepAlive(r.nettyRequest);
    
    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(status), Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
    response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
    
    if(keepAlive) {
      response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
      response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
    }
    
    r.ctx.write(response);
    
    if(!keepAlive) {
      r.ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
  }
}