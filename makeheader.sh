# Compile Java class and put the class file in bin/
javac src/ctrl/SerialJNI.java -d bin/

# Generate header
cd bin && javah ctrl.SerialJNI && cd ..

#Copy header to jni directory
cp bin/ctrl_SerialJNI.h jni/
