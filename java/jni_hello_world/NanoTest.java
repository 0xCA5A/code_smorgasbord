
// http://blog.kapsi.de/blog/a-big-flaw-in-javas-nanotime
// http://www.principiaprogramatica.com/?p=16

public class NanoTest {

    public static void main(String[] args)
    {

        long counter = 0;

        System.out.println("NanoTest - call System.nanoTime() infinite times and assert that nanosTaken >= 0L...");

        for(;;)
        {
            long nanosBefore = System.nanoTime();

            //do something...
            ;;;

            long nanosTaken = System.nanoTime() - nanosBefore;

//             System.out.println("value nr: " + counter + ", nanos taken: " + nanosTaken);
            assert(nanosTaken >= 0L);

            counter++;

        }
    }

}