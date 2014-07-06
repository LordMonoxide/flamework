package flamework.http.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class Initializer extends ChannelInitializer<SocketChannel> {
  @Override protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline p = ch.pipeline();
    
    p.addLast(
      new HttpRequestDecoder(),
      new HttpResponseEncoder(),
      new Handler()
    );
  }
}