
// http://www.karlin.mff.cuni.cz/network/prirucky/javatut/java/threads/simple.html

class ThreadingTest {

    static final int NR_OF_THREADS = 7;

    public static void main (String[] args) {

        System.out.println("[ThreadingTest] start " + NR_OF_THREADS + " threads to do something...");

        for (int i = 0; i < NR_OF_THREADS; i++)
        {
            new WorkerThread("WORKER THREAD " + i).start();
        }
    }
}

