package bootstrap.rewriter;

import java.io.IOException;

public class ConstantNameAndTypeInfo extends ConstantPool {
  public int nameIndex;
  public int descriptorIndex;
  
  public ConstantNameAndTypeInfo(ClassRewriter r, ConstantPoolType t) throws IOException {
    super(r, t);
    
    nameIndex       = r.readU2();
    descriptorIndex = r.readU2();
    
    System.out.println("Name index: " + nameIndex);
    System.out.println("Descriptor index: " + descriptorIndex);
  }
}
