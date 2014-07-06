package bootstrap.rewriter;

import java.io.IOException;

public class ConstantReferenceInfo extends ConstantPool {
  public final int classIndex;
  public final int nameAndTypeIndex;
  
  public ConstantReferenceInfo(ClassRewriter r, ConstantPoolType t) throws IOException {
    super(r, t);
    
    classIndex       = r.readU2();
    nameAndTypeIndex = r.readU2();
    
    System.out.println("Class index: " + classIndex);
    System.out.println("Name and type index: " + nameAndTypeIndex);
  }
}