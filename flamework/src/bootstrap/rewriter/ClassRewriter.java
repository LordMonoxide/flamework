package bootstrap.rewriter;

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
      int constant = readU1();
      System.out.println("Constant " + constant);
      cp[i] = ConstantPoolType.of(constant).instanciate(this);
    }
  }
  
  byte[] read(int size) throws IOException {
    byte b[] = new byte[size];
    _in.read(b, 0, b.length);
    return b;
  }
  
  private int readAsInt(int size) throws IOException {
    byte b[] = read(size);
    
    int n = 0;
    for(int i = 0; i < b.length; i++) {
      n += (b[i] & 0x000000FF) << ((b.length - i - 1) * 8);
    }
    
    return n;
  }
  
  int readU1() throws IOException {
    return readAsInt(1);
  }
  
  int readU2() throws IOException {
    return readAsInt(2);
  }
  
  int readU4() throws IOException {
    return readAsInt(4);
  }
}