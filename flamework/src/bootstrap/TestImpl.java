package bootstrap;

public class TestImpl implements TestInterface {
  @Override public void test() {
    System.out.println("Test");
    
    TestInterface test = new Test();
    test.test();
  }
}