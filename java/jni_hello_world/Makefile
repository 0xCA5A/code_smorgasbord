



all:
	javac HelloWorld.java
	javah -jni HelloWorld
	
	gcc -I/usr/lib/jvm/java-6-openjdk-amd64/include -Wall -fPIC -c HelloWorld.c
	gcc -shared -Wl,-soname,libHelloWorld.so.1 -o libHelloWorld.so.1.0 HelloWorld.o
	ln -s libHelloWorld.so.1.0 libHelloWorld.so.1
	ln -s libHelloWorld.so.1 libHelloWorld.so
	
clean:
	rm -f *~
	rm -f *.so
	rm -f *.so.*
	rm -f *.o
	rm -f *.class
	rm -f HelloWorld.h