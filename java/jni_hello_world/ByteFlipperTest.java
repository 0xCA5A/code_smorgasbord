
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
    private native void flipBytesByteCopyPointerJNI(byte[] myByteArray);
    private native void flipBytesByteShiftPointerJNI(byte[] myByteArray);


    private void flipBytesByteCopyJava(byte[] myByteArray)
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

        System.out.println("\n\n################################################################################");
        System.out.println("# [main] configuration");
        System.out.println("# * BYTE_ARRAY_SIZE: " + BYTE_ARRAY_SIZE);
        System.out.println("################################################################################");

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



        System.out.println("\n################################################################################");
        System.out.println("# [main] empty JNI calls");
        System.out.println("################################################################################");



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



        System.out.println("\n################################################################################");
        System.out.println("# [main] more or less meaningful JNI calls");
        System.out.println("################################################################################");



//         System.out.println("[main] byte array content before flipBytesByteCopyPointerJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesByteCopyPointerJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesByteCopyPointerJNI(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesByteCopyPointerJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);



//         System.out.println("[main] byte array content before flipBytesByteShiftPointerJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesByteShiftPointerJNI(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesByteShiftPointerJNI(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesByteShiftPointerJNI()");
//         myByteFlipperTest.printByteArray(myByteArray);



        System.out.println("\n################################################################################");
        System.out.println("# [main] more or less meaningful Java calls");
        System.out.println("################################################################################");



//         System.out.println("[main] byte array content before flipBytesByteCopyJava()");
//         myByteFlipperTest.printByteArray(myByteArray);

        startTimeInNs = System.nanoTime();
        myByteFlipperTest.flipBytesByteCopyJava(myByteArray);
        stopTimeInNs = System.nanoTime();
        deltaTimeInNs = stopTimeInNs - startTimeInNs;
        System.out.println("[main] time used in function flipBytesByteCopyJava(): " + deltaTimeInNs + "ns");

//         System.out.println("[main] byte array content after flipBytesByteCopyJava()");
//         myByteFlipperTest.printByteArray(myByteArray);



    }

    static {
       //load ByteFlipperTest.so
        System.loadLibrary("ByteFlipperTest");
    }

}