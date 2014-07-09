package flamework.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Request {
  public final String  host;
  public final Method  method;
  public final String  uri;
  public final Headers headers;
  public final Params  params;
  public final String  content;
  
  protected Request(String host, Method method, String uri, Headers headers, Params params, String content) {
    this.host    = host;
    this.method  = method;
    this.uri     = uri;
    this.headers = headers;
    this.params  = params;
    this.content = content;
  }
  
  public void respond(Response response) {
    response.send(this);
  }

  public static class Headers extends ImmutableMap<String, String> {
    public Headers(AdderCallback<String, String> callback) { super(callback); }
  }
  
  public static class Params extends ImmutableMap<String, List<String>> {
    public Params(AdderCallback<String, List<String>> callback) { super(callback); }
  }
  
  protected static class ImmutableMap<K, V> implements Iterable<Map.Entry<K, V>> {
    private Map<K, V> _data = new HashMap<>();
    
    protected ImmutableMap(AdderCallback<K, V> callback) {
      callback.add(new Add());
    }
    
    public V get(K key) {
      return _data.get(key);
    }
    
    @Override public Iterator<Map.Entry<K, V>> iterator() {
      return _data.entrySet().iterator();
    }
    
    public class Add {
      public void add(K key, V value) {
        _data.put(key, value);
      }
    }
  }
  
  public interface AdderCallback<K, V> {
    public void add(ImmutableMap<K, V>.Add map);
  }
  
  public enum Method {
    GET, POST, PUT, DELETE;
  }
}