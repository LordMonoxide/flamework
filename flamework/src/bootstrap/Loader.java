package bootstrap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bootstrap.rewriter.ClassRewriter;

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
    String file = override.replace('.', '/');
    byte[] b = null;
    
    try {
      ClassRewriter r = new ClassRewriter(getClass().getClassLoader().getResourceAsStream(file + ".class"));
      r.parse();
      r.rewrite(override.replace('.', '/'), name.replace('.', '/'));
      r.rewrite(override.substring(override.lastIndexOf('.') + 1), name.substring(name.lastIndexOf('.') + 1));
      b = r.commit();
      
      Class<?> c = defineClass(name, b, 0, b.length);
      resolveClass(c);
      return c;
    } catch(IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
}