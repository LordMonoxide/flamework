package bootstrap.rewriter;

import java.io.IOException;

public class ConstantStringInfo extends ConstantPool {
  public final int stringIndex;
  
  public ConstantStringInfo(ClassRewriter r, ConstantPoolType t) throws IOException {
    super(r, t);
    
    stringIndex = r.readU2();
    
    System.out.println("String index: " + stringIndex);
  }
}
