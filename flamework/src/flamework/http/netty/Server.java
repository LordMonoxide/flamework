package flamework.http.netty;

import flamework.http.ServerInterface;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server implements ServerInterface {
  private EventLoopGroup  _bossGroup;
  private EventLoopGroup  _workGroup;
  private ServerBootstrap _server;
  private Channel         _channel;
  
  private Events _events = new Events();
  
  public Server() {
    _bossGroup = new NioEventLoopGroup();
    _workGroup = new NioEventLoopGroup();
    
    _server = new ServerBootstrap()
      .group(_bossGroup, _workGroup)
      .channel(NioServerSocketChannel.class)
      .childHandler(new Initializer(_events));
  }
  
  @Override public Events events() {
    return _events;
  }
  
  public void listen(int port) {
    if(_channel != null) { return; }
    
    _server.bind(port).addListener(f -> {
      if(f.isSuccess()) {
        _channel = ((ChannelFuture)f).channel();
      } else {
        _events.raiseError(f.cause());
      }
      
      _events.raiseBind();
    });
  }
  
  public void close() {
    if(_channel == null) { return; }
    
    _channel.close().addListener(f -> {
      if(f.isSuccess()) {
        _channel = null;
      } else {
        _events.raiseError(f.cause());
      }
      
      _events.raiseClose();
    });
  }
  
  public void destroy() {
    _bossGroup.shutdownGracefully();
    _workGroup.shutdownGracefully();
  }
}