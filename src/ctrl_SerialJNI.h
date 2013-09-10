/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ctrl_SerialJNI */

#ifndef _Included_ctrl_SerialJNI
#define _Included_ctrl_SerialJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ctrl_SerialJNI
 * Method:    open_port
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_ctrl_SerialJNI_open_1port
  (JNIEnv *, jobject, jstring);

/*
 * Class:     ctrl_SerialJNI
 * Method:    write
 * Signature: (IC)I
 */
JNIEXPORT jint JNICALL Java_ctrl_SerialJNI_write
  (JNIEnv *, jobject, jint, jchar);

/*
 * Class:     ctrl_SerialJNI
 * Method:    read_char
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_ctrl_SerialJNI_read_1char
  (JNIEnv *, jobject, jint);

/*
 * Class:     ctrl_SerialJNI
 * Method:    close
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_ctrl_SerialJNI_close
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
