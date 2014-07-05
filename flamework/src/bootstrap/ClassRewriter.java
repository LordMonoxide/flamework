package bootstrap;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassRewriter {
  BufferedInputStream _in;
  
  public ClassRewriter(InputStream file) throws FileNotFoundException {
    _in = new BufferedInputStream(file);
  }
  
  public void parse() throws IOException {
    int magic = readU4();
    int minor = readU2();
    int major = readU2();
    
    int cpsize = readU2();
    
    System.out.println("Magic: "   + Integer.toHexString(magic));
    System.out.println("Version: " + Integer.toHexString(major) + '.' + Integer.toHexString(minor));
    System.out.println("Constant pool size: " + Integer.toHexString(cpsize));
    
    ConstantPool cp[] = new ConstantPool[cpsize - 1];
    
    for(int i = 0; i < cp.length; i++) {
      cp[i] = new ConstantPool();
    }
  }
  
  private int read(int size) throws IOException {
    byte b[] = new byte[size];
    _in.read(b, 0, b.length);
    
    int n = 0;
    for(int i = 0; i < b.length; i++) {
      n += (b[i] & 0x000000FF) << ((b.length - i - 1) * 8);
    }
    
    return n;
  }
  
  private int readU1() throws IOException {
    return read(1);
  }
  
  private int readU2() throws IOException {
    return read(2);
  }
  
  private int readU4() throws IOException {
    return read(4);
  }
  
  private class ConstantPool {
    public final ConstantPoolType type;
    
    private ConstantPool() throws IOException {
      int i = readU2();
      System.out.println(i);
      type = ConstantPoolType.of(i);
      
      System.out.println("Constant type: " + type);
    }
  }
  
  private enum ConstantPoolType {
    STRING    (0x1, "String"),
    INTEGER   (0x3, "Integer"),
    FLOAT     (0x4, "Float"),
    LONG      (0x5, "Long"),
    DOUBLE    (0x6, "Double"),
    CLASS     (0x7, "Class"),
    RSTRING   (0x8, "String Ref"),
    RFIELD    (0x9, "Field Ref"),
    RMETHOD   (0xA, "Method Ref"),
    RINTERFACE(0xB, "Interface Ref"),
    NAMETYPE  (0xC, "Name/Type");
    
    private int    _val;
    private String _name;
    
    ConstantPoolType(int val, String name) {
      _val  = val;
      _name = name;
    }
    
    @Override public String toString() {
      return _name;
    }
    
    public static ConstantPoolType of(int type) {
      for(ConstantPoolType t : ConstantPoolType.values()) {
        if(t._val == type) {
          return t;
        }
      }
      
      return null;
    }
  }
}