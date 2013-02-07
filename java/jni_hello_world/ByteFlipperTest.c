#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "ByteFlipperTest.h"


//hope this is not optimized away...
JNIEXPORT void JNICALL Java_ByteFlipperTest_emptyCallJNI(JNIEnv* env, jobject obj)
{
}


JNIEXPORT void JNICALL Java_ByteFlipperTest_emptyCallGetAndReleaseByteArrayElementsJNI(JNIEnv* env, jobject obj, jbyteArray byte_array)
{
//     printf("[%s] hello from function\n", __func__);

    //get direct pointer to the elements
    jbyte* p_byte_array = (jbyte*) (*env)->GetByteArrayElements(env, byte_array, NULL);

    //exception occured?
    if (p_byte_array == NULL) {
        exit(-1);
    }

    //release pointer
    (*env)->ReleaseByteArrayElements(env, byte_array, p_byte_array, 0);
}


JNIEXPORT void JNICALL Java_ByteFlipperTest_emptyCallGetAndReleasePrimitiveArrayCriticalJNI(JNIEnv* env, jobject obj, jbyteArray byte_array)
{
//     printf("[%s] hello from function\n", __func__);

    //get direct pointer to the elements
    jbyte* p_byte_array = (jbyte*) (*env)->GetPrimitiveArrayCritical(env, byte_array, NULL);

    //exception occured?
    if (p_byte_array == NULL) {
        exit(-1);
    }

    //release pointer
    (*env)->ReleasePrimitiveArrayCritical(env, byte_array, p_byte_array, 0);
}


JNIEXPORT void JNICALL Java_ByteFlipperTest_flipBytesByteCopyPointerJNI(JNIEnv* env, jobject obj, jbyteArray byte_array)
{
    //check here
    //http://192.9.162.55/docs/books/jni/html/objtypes.html, 3.3.2 Accessing Arrays of Primitive Types

//     printf("[%s] hello from function\n", __func__);

    int i;
    jbyte tmp_byte_value;

    //get direct pointer to the elements
    jbyte* p_byte_array = (jbyte*) (*env)->GetPrimitiveArrayCritical(env, byte_array, NULL);

    //exception occured?
//     if (p_byte_array == NULL) {
//         printf("[!] CANT ACCESS ARRAY, GOT NULL POINTER!\n");
//         exit(-1);
//     }

    for(i = 0; i < (ByteFlipperTest_BYTE_ARRAY_SIZE - 1) / sizeof(char); i = i+2)

    {
        tmp_byte_value = p_byte_array[i];
        p_byte_array[i] = p_byte_array[i+1];
        p_byte_array[i+1] = tmp_byte_value;
    }
    //release pointer
    (*env)->ReleasePrimitiveArrayCritical(env, byte_array, p_byte_array, 0);

}


JNIEXPORT void JNICALL Java_ByteFlipperTest_flipBytesByteShiftPointerJNI(JNIEnv* env, jobject obj, jbyteArray byte_array)
{
    //check here
    //http://192.9.162.55/docs/books/jni/html/objtypes.html, 3.3.2 Accessing Arrays of Primitive Types

//     printf("[%s] hello from function\n", __func__);

    int i;

    //get direct pointer to the elements
    jshort* p_short_pointer_to_byte_array = (jshort*) (*env)->GetPrimitiveArrayCritical(env, byte_array, NULL);

    //exception occured?
//     if (p_short_pointer_to_byte_array == NULL) {
//         printf("[!] CANT ACCESS ARRAY, GOT NULL POINTER!\n");
//         exit(-1);
//     }

    for(i = 0; i < ByteFlipperTest_BYTE_ARRAY_SIZE / sizeof(short); i++)
    {
        //no local variable, let the compiler optimize this...
        p_short_pointer_to_byte_array[i] = ((p_short_pointer_to_byte_array[i] << 8) & 0xff00) | ((p_short_pointer_to_byte_array[i] >> 8) & 0x00ff);
    }

    //release pointer
    (*env)->ReleasePrimitiveArrayCritical(env, byte_array, p_short_pointer_to_byte_array, 0);

}