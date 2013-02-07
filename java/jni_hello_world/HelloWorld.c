#include <jni.h>
#include <stdio.h>
#include "HelloWorld.h"


//hope this is not optimized away...
JNIEXPORT void JNICALL Java_HelloWorld_emptyCall(JNIEnv* env, jobject obj)
{
}


JNIEXPORT void JNICALL Java_HelloWorld_flipBytes(JNIEnv* env, jobject obj, jbyteArray byte_array)
{

    int i;
    jbyte tmp_value;

    
    //copy everything into a local buffer, this is pain...
//     (*env)->GetIntArrayRegion(env, byte_array, 0, HelloWorld_BYTE_ARRAY_SIZE - 1, buf);

//     direct pointer to the elements
    jbyte* p_byte_array;
    p_byte_array = (jbyte*) (*env)->GetByteArrayElements(env, byte_array, NULL);
    for(i = 0; i < HelloWorld_BYTE_ARRAY_SIZE - 1; i=i+2)
    {
        tmp_value = p_byte_array[i];
        p_byte_array[i] = p_byte_array[i+1];
        p_byte_array[i+1] = tmp_value;
    }
    (*env)->ReleaseByteArrayElements(env, byte_array, p_byte_array, 0);
    printf("~ hello from %s\n", __func__);


//     _jarray

// check jni.h for further details
//     typedef unsigned short  jchar;


}