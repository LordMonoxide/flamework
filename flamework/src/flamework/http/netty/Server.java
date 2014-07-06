package flamework.http.netty;

import flamework.http.ServerInterface;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server implements ServerInterface {
  private EventLoopGroup  _bossGroup;
  private EventLoopGroup  _workGroup;
  private ServerBootstrap _server;
  
  public Server() {
    _bossGroup = new NioEventLoopGroup();
    _workGroup = new NioEventLoopGroup();
    
    _server = new ServerBootstrap()
      .group(_bossGroup, _workGroup)
      .channel(NioServerSocketChannel.class)
      .childHandler(new Initializer());
  }
  
  public ChannelFuture listen(int port) {
    return _server.bind(port);
  }
}