# Compile Java class and put the class file in bin/
javac src/target/SerialJNI.java -d bin/

# Generate header
cd bin && javah target.SerialJNI && cd ..

#Copy header to jni directory
cp bin/target_SerialJNI.h jni/
