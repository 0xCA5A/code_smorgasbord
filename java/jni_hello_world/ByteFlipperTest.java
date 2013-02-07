
// source
// http://192.9.162.55/docs/books/jni/html/start.html



class ByteFlipperTest
{

    private static final int BYTE_ARRAY_SIZE = 4 * 1024;


    public void printByteArray(byte[] myByteArray)
    {
        System.out.println("################################################################################");
        System.out.println("# byte[] content:");
        System.out.println("################################################################################");

        for (int i = 0; i < myByteArray.length; i++)
        {
//             System.out.println("# index " + i + ": " + String.format("%x", myByteArray[i]));
            System.out.printf("# index " + i + ": 0x%02x\n", myByteArray[i]);
        }

        System.out.println("################################################################################");
    }


    //definition of native functions
    private native void emptyCallJNI();
    private native void emptyCallGetAndReleaseByteArrayElementsJNI(byte[] myByteArray);
    private native void emptyCallGetAndReleasePrimitiveArrayCriticalJNI(byte[] myByteArray);
    private native void flipBytesByteCopyJNI(byte[] myByteArray);
    private native void flipBytesByteShiftJNI(byte[] myByteArray);


    private void flipBytesJava(byte[] myByteArray)
    {
        byte tmp_value;
        for(int i = 0; i < myByteArray.length; i = i+2)
        {
        tmp_value = myByteArray[i];
        myByteArray[i] = myByteArray[i+1];
        myByteArray[i+1] = tmp_value;
        }
    }

    public static void main(String[] args) {

        System.out.println("[main] test configuration:");
        System.out.println(" * BYTE_ARRAY_SIZE: " + BYTE_ARRAY_SIZE);

        //declare and init byte array
        byte[] myByteArray = new byte[BYTE_ARRAY_SIZE];
        for(int i = 0; i < myByteArray.length; i++)
        {
            myByteArray[i] = (byte) (i % 0xff);
        }

        long startTimeInNs;
        long stopTimeInNs;
        long deltaTimeInNs;

        ByteFlipperTest myByteFlipperTest = new ByteFlipperTest();



        startTimeInNs = System.nanoTime();
        myByteFlipperTest.emptyCallJNI();
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function emptyCallJNI(): " + deltaTimeInNs + "ns");



        startTimeInNs = System.nanoTime();
        myByteFlipperTest.emptyCallGetAndReleaseByteArrayElementsJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function emptyCallGetAndReleaseByteArrayElementsJNI(): " + deltaTimeInNs + "ns");



        startTimeInNs = System.nanoTime();
        myByteFlipperTest.emptyCallGetAndReleasePrimitiveArrayCriticalJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function emptyCallGetAndReleasePrimitiveArrayCriticalJNI(): " + deltaTimeInNs + "ns");



//         System.out.println("[main] byte array content before flipBytesJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesByteCopyJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesByteCopyJNI(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);



//         System.out.println("[main] byte array content before flipBytesJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesByteShiftJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesByteShiftJNI(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);



//         System.out.println("[main] byte array content before flipBytesJava()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesJava(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesJava(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesJava()");
//         myByteFlipperTest.printByteArray(myByteArray);



    }

    static {
       //load ByteFlipperTest.so
        System.loadLibrary("ByteFlipperTest");
    }

}