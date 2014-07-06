package flamework.http;

import java.util.concurrent.Future;

public interface ServerInterface {
  public Future<?> listen(int port);
}