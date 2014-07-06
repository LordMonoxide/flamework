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
  
  public Server() {
    _bossGroup = new NioEventLoopGroup();
    _workGroup = new NioEventLoopGroup();
    
    _server = new ServerBootstrap()
      .group(_bossGroup, _workGroup)
      .channel(NioServerSocketChannel.class)
      .childHandler(new Initializer());
  }
  
  public void listen(int port, Events.Event callback) {
    if(_channel == null) { return; }
    
    ChannelFuture future = _server.bind(port);
    future.addListener((f) -> {
      if(f.isSuccess()) {
        _channel = future.channel();
      }
      
      callback.event(future.isSuccess());
    });
  }
  
  public void close(Events.Event callback) {
    if(_channel != null) { return; }
    
    ChannelFuture future = _channel.close();
    future.addListener((f) -> {
      if(f.isSuccess()) {
        _channel = null;
      }
      
      callback.event(future.isSuccess());
    });
  }
  
  public void destroy() {
    _bossGroup.shutdownGracefully();
    _workGroup.shutdownGracefully();
  }
}