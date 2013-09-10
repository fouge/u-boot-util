# create lib
cd jni && gcc -fPIC -o libserial.so -shared -I/usr/lib/jvm/java-7-openjdk-amd64/include/ -I/usr/lib/jvm/java-7-openjdk-amd64/include/linux/ serial.c
cd .. 


# copy lib to lib/
cp jni/libserial.so lib