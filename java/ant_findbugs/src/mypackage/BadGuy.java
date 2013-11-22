
package mypackage;


import java.util.Random;


public class BadGuy
{

  class BadThread extends Thread {

    String name;
    Random generator = new Random();
    int myRandomInteger = 0;

    public BadThread(String name)
    {

      System.out.println("[" + name + "] constructor!");
      this.name = name;

    }

    public void run() {
      for(int i = 0; i < 5; i++) {
        try {
          sleep(globalInt);
        }
        catch(InterruptedException e) {
        }
        System.out.println("hello from " + name);
        globalInt = generator.nextInt(5000);
        System.out.println("[" + name + "] globalInt set to value: " + globalInt );

      }
    }
  }


  public int[] array = new int[12312312];
  String name;
  int globalInt = 42;
  BadThread badThreadA;
  BadThread badThreadB;



  public BadGuy(String name)
  {
    this.name = name;
    badThreadA = new BadThread("badThreadA");
    badThreadB = new BadThread("badThreadB");
  }


  public void sayHello()
  {
    System.out.println("sayHello " + name);

    if (badThreadA != null && badThreadA != badThreadB)
    {
      badThreadA.start();
      badThreadB.start();
    }

    //dead array somewhere in the space...
    array = new int[24];

    int i = 0;
    while (true)
    {

      i += 1000;

      if (i > 0xBEEF)
      {
        return;
      }

    }
  }
}
