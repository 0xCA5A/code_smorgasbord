
import mypackage.*;


public class HelloWorld
{

  String jvm;
  String osName;
  String osVersion; 

  String name;

  Foo foo;
  Bar bar;
  BadGuy badGuy;



  public static void main(String args[])
  {
    HelloWorld helloWorld = new HelloWorld("helloWorld");
    helloWorld.run();
  }



  public HelloWorld(String name)
  {

    this.name = name;

    jvm = System.getProperty("java.version");
    osName = System.getProperty("os.name");
    osVersion = System.getProperty("os.version");

    this.sayHello();
    foo = new Foo("foo");
    bar = new Bar("bar");
    badGuy = new BadGuy("badGuy");

  }
 


  public void run()
  {
    foo.sayHello();
    bar.sayHello();
    badGuy.sayHello();
  }

 

  public void sayHello()
  {
    System.out.println("sayHello " + this.name);
    System.out.println("java version: " + jvm);
    System.out.println("os name: " + osName);
    System.out.println("os version: " + osVersion);
  }


}