
import mypackage.*;


public class HelloWorld
{

  private String jvmVersion;
  private String osName;
  private String osVersion; 

  private String name;

  private Foo foo;
  private Bar bar;
  private BadGuy badGuy;



  public static void main(String args[])
  {
    HelloWorld helloWorld = new HelloWorld("helloWorld");
    helloWorld.run();
  }



  public HelloWorld(String name)
  {
    this.name = name;

    //horrible...
    Boolean myBoolean = true;
    if (myBoolean = true)
    {
      jvmVersion = System.getProperty("java.version");
      osName = System.getProperty("os.name");
      osVersion = System.getProperty("os.version");
    }

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
    System.out.println("java version: " + jvmVersion);
    System.out.println("os name: " + osName);
    System.out.println("os version: " + osVersion);
  }


}