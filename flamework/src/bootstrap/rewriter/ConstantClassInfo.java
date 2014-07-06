package bootstrap.rewriter;

import java.io.IOException;

public class ConstantClassInfo extends ConstantPool {
  public final int index;
  
  public ConstantClassInfo(ClassRewriter r, ConstantPoolType t) throws IOException {
    super(r, t);
    
    index = r.readU2();
    
    System.out.println("Class name index: " + index);
  }
}