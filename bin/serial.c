/*
 * serial.c
 */

#include <jni.h>
#include <stdio.h>
#include "ctrl_SerialJNI.h"
#include <string.h>  /* String function definitions */
#include <unistd.h>  /* UNIX standard function definitions */
#include <fcntl.h>   /* File control definitions */
#include <errno.h>   /* Error number definitions */
#include <termios.h> /* POSIX terminal control definitions */

/*
 *		gcc -fPIC -o libserial.so -shared -I/usr/lib/jvm/java-7-openjdk-amd64/include/ -I/usr/lib/jvm/java-7-openjdk-amd64/include/linux/ serial.c
 */


/*
 * Class:     ctrl_JNI
 * Method:    open_port
 * Signature: (Ljava/lang/String;)I
 *
 * Returns the file descriptor on success or -1 on error.
 */
JNIEXPORT jint JNICALL Java_ctrl_SerialJNI_open_1port (JNIEnv * env, jobject obj, jstring str){
  int fd; /* File descriptor for the port */

const char *file = (*env)->GetStringUTFChars(env, str, 0);

  fd = open(file, O_RDWR | O_NOCTTY | O_NDELAY);
  if (fd == -1)
  {
   /*
    * Could not open the port.
    */

    perror("open_port: Unable to open /dev/ttyf1 - ");
  }
  else
    fcntl(fd, F_SETFL, 0);

  return (fd);
}


