
# CFLAGS = -Wall -Werror -march=pentium-m -O2 -pipe -fomit-frame-pointer
# CFLAGS = -Wall -Werror -march=corei7-avx -O3 -pipe -fomit-frame-pointer


all:
	javac ByteFlipperTest.java
	javah -jni ByteFlipperTest
	
	gcc $(CFLAGS) -I/usr/lib/jvm/java-6-openjdk-amd64/include -Wall -fPIC -c ByteFlipperTest.c
	gcc $(CFLAGS) -shared -Wl,-soname,libByteFlipperTest.so.1 -o libByteFlipperTest.so.1.0 ByteFlipperTest.o
	rm -f libByteFlipperTest.so.1; ln -s libByteFlipperTest.so.1.0 libByteFlipperTest.so.1
	rm -f libByteFlipperTest.so; ln -s libByteFlipperTest.so.1 libByteFlipperTest.so
	
clean:
	rm -f *~
	rm -f *.so
	rm -f *.so.*
	rm -f *.o
	rm -f *.class
	rm -f ByteFlipperTest.h