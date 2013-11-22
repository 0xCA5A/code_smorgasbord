
package mypackage;

public class Bar
{


  private String name;
  private int[] crap = new int[1324123];


  Bar()
  {
    //NOTE: this is for nothing...
    for(int i = 0; i < crap.length; ++i)
    {
      crap[i] = i;
    }
  }


  public Bar(String name)
  {
    this.name = name;
  }


  public void sayHello()
  {
    System.out.println("sayHello " + name);
  }

}
