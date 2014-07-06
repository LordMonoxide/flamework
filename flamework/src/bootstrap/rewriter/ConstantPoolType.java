package bootstrap.rewriter;

import java.lang.reflect.InvocationTargetException;

enum ConstantPoolType {
  UTF8      (0x1, "UTF-8", ConstantUTF8Info.class),
  INTEGER   (0x3, "Integer", null),
  FLOAT     (0x4, "Float", null),
  LONG      (0x5, "Long", null),
  DOUBLE    (0x6, "Double", null),
  CLASS     (0x7, "Class", ConstantClassInfo.class),
  RSTRING   (0x8, "String Ref", ConstantStringInfo.class),
  RFIELD    (0x9, "Field Ref", ConstantReferenceInfo.class),
  RMETHOD   (0xA, "Method Ref", ConstantReferenceInfo.class),
  RINTERFACE(0xB, "Interface Ref", ConstantReferenceInfo.class),
  NAMETYPE  (0xC, "Name/Type", ConstantNameAndTypeInfo.class);
  
  private int    _val;
  private String _name;
  private Class<? extends ConstantPool> _class;
  
  ConstantPoolType(int val, String name, Class<? extends ConstantPool> c) {
    _val   = val;
    _name  = name;
    _class = c;
  }
  
  @Override public String toString() {
    return _name;
  }
  
  public ConstantPool instanciate(ClassRewriter r) {
    try {
      return _class.getConstructor(ClassRewriter.class, ConstantPoolType.class).newInstance(r, this);
    } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    
    return null;
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