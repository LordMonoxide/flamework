package bootstrap;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Loader extends ClassLoader {
  public static void main(String[] args) throws Exception {
    Loader loader = new Loader(Loader.class.getClassLoader());
    loader.addMapping("bootstrap.Test", "bootstrap.TestImpl");
    TestInterface test = (TestInterface)loader.loadClass("bootstrap.Test").newInstance();
    System.out.println(test.getClass().getClassLoader());
    test.test();
  }
  
  private Map<String, String> _override = new HashMap<>();
  
  public Loader(ClassLoader parent) {
    super(parent);
  }
  
  public void addMapping(String request, String override) {
    _override.put(request, override);
  }
  
  @Override public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    String override = _override.get(name);
    if(override != null) {
      System.out.println("Overriding class '" + name + "'");
      //return super.loadClass(override);
      Class<?> c = findLoadedClass(override);
      if(c == null) {
        c = getClass(name, override);
      }
      
      return c;
    }
    
    System.out.println("Loading class '" + name + "'");
    return super.loadClass(name, false);
  }
  
  private Class<?> getClass(String name, String override) throws ClassNotFoundException {
    String file = override.replace('.', File.separatorChar);
    byte[] b = null;
    
    try {
      b = loadClassData(file + ".class");
      
      byte n[] = file.getBytes();
      byte b2[][] = new byte[3][];
      b2[0] = new byte[14];
      System.arraycopy(b, 0, b2[0], 0, 14);
      
      b2[1] = new byte[2 + n.length];
      b2[1][0] = (byte)((n.length & 0x0000FF00) >> 2);
      b2[1][1] = (byte) (n.length & 0x000000FF);
      System.arraycopy(n, 0, b2[1], 2, n.length);
      
      int l = override.getBytes().length;
      
      b2[2] = new byte[b.length - 2 - 14 - l];
      System.arraycopy(b, b.length - b2[2].length, b2[2], 0, b2[2].length);
      
      b = new byte[b2[0].length + b2[1].length + b2[2].length];
      System.arraycopy(b2[0], 0, b, 0, b2[0].length);
      System.arraycopy(b2[1], 0, b, b2[0].length, b2[1].length);
      System.arraycopy(b2[2], 0, b, b2[0].length + b2[1].length, b2[2].length);
      
      Class<?> c = defineClass(name, b, 0, b.length);
      resolveClass(c);
      return c;
    } catch(IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  private byte[] loadClassData(String name) throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
    int size = stream.available();
    byte buff[] = new byte[size];
    DataInputStream in = new DataInputStream(stream);
    in.readFully(buff);
    in.close();
    return buff;
  }
}