
// for excellent jni examples, check here:
// http://192.9.162.55/docs/books/jni/html/start.html



import java.util.Random;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.Arrays;


class ByteFlipperTest
{

    private static final int BYTE_ARRAY_SIZE        = 4 * 1024;
    private static final int NR_OF_FUNCTION_CALLS   = 2048;


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
    private native void flipBytesByteXORPointerJNI(byte[] myByteArray);
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

    private byte[] getNewByteArrayRandom(int byteArrayLength)
    {
        Random randomGenerator = new Random();
        byte[] newByteArray = new byte[byteArrayLength];
        for(int i = 0; i < newByteArray.length; i++)
        {
            newByteArray[i] = (byte) randomGenerator.nextInt(0xff);
        }

        return newByteArray;
    }

    private byte[] getNewByteArrayAsc(int byteArrayLength)
    {
        byte[] newByteArray = new byte[byteArrayLength];
        for(int i = 0; i < newByteArray.length; i++)
        {
            newByteArray[i] = (byte) (i % 0xff);
        }

        return newByteArray;
    }

    //http://de.wikipedia.org/wiki/Reflexion_%28Programmierung%29#Java
    private void invokeTestMethodByName(Object object, String methodName, int nrOfRuns) {

        long startTimeInNs;
        long stopTimeInNs;
        long deltaTimeInNs;

        //get an initialized byte array
        byte[] myByteArray = getNewByteArrayRandom(BYTE_ARRAY_SIZE);
        Object[] nullArgument = new Object[0];

        //clean up
        System.gc();
        System.out.flush();

        Class[] argTypes = null;
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods)
        {

            //is this the right way to do this? or by exception?
            if (method.getName() == methodName)
            {
                if (method.getParameterTypes().length == 0)
                {
                    argTypes = new Class[0];
                }
                else
                {
                    argTypes = new Class[] { byte[].class };
                }
            }
        }

        try {
            Method method = object.getClass().getDeclaredMethod(methodName, argTypes);
            for(int i = 0; i < nrOfRuns; i++)
            {
                startTimeInNs = System.nanoTime();

                if (method.getParameterTypes().length == 0)
                {
                    startTimeInNs = System.nanoTime();
                    method.invoke(object, nullArgument);
                    stopTimeInNs = System.nanoTime();
                }
                else
                {
                    startTimeInNs = System.nanoTime();
                    method.invoke(object, myByteArray);
                    stopTimeInNs = System.nanoTime();
                }

                deltaTimeInNs = stopTimeInNs - startTimeInNs;
                System.out.println("[invokeTestMethodByName] time used in function " + methodName +": " + deltaTimeInNs + "ns");
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.exit(-1);
        }
    }

    public static void main(String[] args) {

        System.out.println("\n\n################################################################################");
        System.out.println("# [main] configuration");
        System.out.println("# * BYTE_ARRAY_SIZE: " + BYTE_ARRAY_SIZE);
        System.out.println("################################################################################");

        ByteFlipperTest myByteFlipperTest = new ByteFlipperTest();



        System.out.println("\n################################################################################");
        System.out.println("# [main] empty JNI calls");
        System.out.println("################################################################################");

        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "emptyCallJNI", NR_OF_FUNCTION_CALLS);
        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "emptyCallGetAndReleaseByteArrayElementsJNI", NR_OF_FUNCTION_CALLS);
        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "emptyCallGetAndReleasePrimitiveArrayCriticalJNI", NR_OF_FUNCTION_CALLS);



        System.out.println("\n################################################################################");
        System.out.println("# [main] more or less meaningful JNI calls");
        System.out.println("################################################################################");

        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "flipBytesByteCopyPointerJNI", NR_OF_FUNCTION_CALLS);
        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "flipBytesByteXORPointerJNI", NR_OF_FUNCTION_CALLS);
        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "flipBytesByteShiftPointerJNI", NR_OF_FUNCTION_CALLS);



        System.out.println("\n################################################################################");
        System.out.println("# [main] more or less meaningful Java calls");
        System.out.println("################################################################################");

        myByteFlipperTest.invokeTestMethodByName(myByteFlipperTest, "flipBytesByteCopyJava", NR_OF_FUNCTION_CALLS);

    }


    static {
       //load the shared object
        System.loadLibrary("ByteFlipperTest");
    }

}
