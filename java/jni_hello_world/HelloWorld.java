
// source
// http://192.9.162.55/docs/books/jni/html/start.html





class HelloWorld
{

    private static final int BYTE_ARRAY_SIZE = 4;;


    public void printByteArray(byte[] myByteArray)
    {
        System.out.println("################################################################################");
        System.out.println("# byte[] content:");
        System.out.println("################################################################################");

        for (int i = 0; i < myByteArray.length; i++)
        {
//             System.out.println("# index " + i + ": " + String.format("%x", myByteArray[i]));
            System.out.printf("# index " + i + ": %02x\n", myByteArray[i]);

        }

        System.out.println("################################################################################");
    }


    private native void emptyCall();
    private native void flipBytes(byte[] myByteArray);


    public static void main(String[] args) {

        byte[] myByteArray = {0x01, 0x07, (byte)0xAB, (byte)0xCD};

        long startTimeInNs;
        long stopTimeInNs;
        long deltaTimeInNs;

        HelloWorld myHelloWorld = new HelloWorld();


        startTimeInNs = System.nanoTime();
        myHelloWorld.emptyCall();
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[i] time used in function emptyCall(): " + deltaTimeInNs + "ns");



        System.out.println("[i] byte array content after flipBytes()");
        myHelloWorld.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myHelloWorld.flipBytes(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[i] time used in function flipBytes(): " + deltaTimeInNs + "ns");

        System.out.println("[i] byte array content after flipBytes()");
        myHelloWorld.printByteArray(myByteArray);




    }

    static {
       //load libHelloWorld.so
        System.loadLibrary("HelloWorld");
    }

}