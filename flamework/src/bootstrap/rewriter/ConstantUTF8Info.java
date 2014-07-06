package bootstrap.rewriter;

import java.io.IOException;

public class ConstantUTF8Info extends ConstantPool {
  public final int    length;
  public final byte[] data;
  
  public ConstantUTF8Info(ClassRewriter r, ConstantPoolType t) throws IOException {
    super(r, t);
    
    length = r.readU2();
    data   = r.read(length);
    
    System.out.println("Read UTF-8 data: " + new String(data, "UTF-8"));
  }
}