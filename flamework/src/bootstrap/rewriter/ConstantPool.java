package bootstrap.rewriter;

import java.io.IOException;

public abstract class ConstantPool {
  public final ConstantPoolType type;
  
  public ConstantPool(ClassRewriter r, ConstantPoolType t) throws IOException {
    type = t;
    
    System.out.println("Constant type: " + type);
  }
}