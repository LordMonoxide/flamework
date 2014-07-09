package flamework.http.netty;

import java.util.Set;

import flamework.http.ServerInterface;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

public class Handler extends SimpleChannelInboundHandler<HttpObject> {
  private static final AsciiString CONTENT_TYPE   = new AsciiString("Content-Type");
  private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
  private static final AsciiString CONNECTION     = new AsciiString("Connection");
  
  private ServerInterface.Events _events;
  
  private HttpRequest   request;
  private StringBuilder _content = new StringBuilder();
  
  Handler(ServerInterface.Events events) {
    _events = events;
  }
  
  @Override public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }
  
  @Override protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
    if(msg instanceof HttpRequest) {
      HttpRequest request = this.request = (HttpRequest)msg;
      
      if(HttpHeaders.is100ContinueExpected(request)) {
        send100Continue(ctx);
      }
      
      appendDecoderResult(request);
    }
    
    if(msg instanceof HttpContent) {
      HttpContent httpContent = (HttpContent)msg;
      
      ByteBuf content = httpContent.content();
      if(content.isReadable()) {
        _content.append(content.toString(CharsetUtil.UTF_8));
        appendDecoderResult(request);
      }
      
      if(msg instanceof LastHttpContent) {
        //LastHttpContent trailer = (LastHttpContent)msg;
        
        _events.raiseRequest(new Request(request, new QueryStringDecoder(request.uri()).parameters(), _content.toString(), ctx));
        
        /*if(!writeResponse(trailer, ctx)) {
          // If keep-alive is off, close the connection once the content is
          // fully written.
          ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }*/
      }
    }
  }
  
  @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    _events.raiseError(cause);
    ctx.close();
  }
  
  private static void appendDecoderResult(HttpObject o) {
    DecoderResult result = o.decoderResult();
    if(result.isSuccess()) {
      return;
    }
    
    //TODO: DecoderError
    
    //buf.append(".. WITH DECODER FAILURE: ");
    //buf.append(result.cause());
    //buf.append("\r\n");
  }
  
  private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx) {
    // Decide whether to close the connection or not.
    boolean keepAlive = HttpHeaders.isKeepAlive(request);
    // Build the response object.
    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, currentObj.decoderResult().isSuccess() ? HttpResponseStatus.OK : HttpResponseStatus.BAD_REQUEST, Unpooled.copiedBuffer("Test", CharsetUtil.UTF_8));
    
    response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
    
    if(keepAlive) {
      // Add 'Content-Length' header only for a keep-alive connection.
      response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
      // Add keep alive header as per:
      // -
      // http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
      response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
    }
    
    // Encode the cookie.
    String cookieString = request.headers().get(HttpHeaders.Names.COOKIE);
    if(cookieString != null) {
      Set<Cookie> cookies = CookieDecoder.decode(cookieString);
      if(!cookies.isEmpty()) {
        // Reset the cookies if necessary.
        for(Cookie cookie : cookies) {
          response.headers().add(HttpHeaders.Names.SET_COOKIE, ServerCookieEncoder.encode(cookie));
        }
      }
    } else {
      // Browser sent no cookie. Add some.
      response.headers().add(HttpHeaders.Names.SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
      response.headers().add(HttpHeaders.Names.SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
    }
    
    // Write the response.
    ctx.write(response);
    
    return keepAlive;
  }
  
  private static void send100Continue(ChannelHandlerContext ctx) {
    ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
  }
}