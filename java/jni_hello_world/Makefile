
# check gentoo cflags for further optimizations
# http://en.gentoo-wiki.com/wiki/Safe_Cflags/Intel

# use this command to check whicht features are enabled by march switch
# $ gcc -c -Q -march=native --help=target



COMMON_FLAGS = -Wall -Werror


# guido
CFLAGS = -march=core2 -O2 -pipe -fomit-frame-pointer

# f14
# CFLAGS = -march=pentium-m -O2 -pipe -fomit-frame-pointer

# nemo
# CFLAGS = -march=corei7-avx -O2 -pipe -fomit-frame-pointer



all:
	javac ByteFlipperTest.java
	javah -jni ByteFlipperTest
	
	gcc $(CFLAGS) $(COMMON_CFLAGS) -I/usr/lib/jvm/java-6-openjdk-amd64/include -Wall -fPIC -c ByteFlipperTest.c
	gcc $(CFLAGS) $(COMMON_CFLAGS) -shared -Wl,-soname,libByteFlipperTest.so.1 -o libByteFlipperTest.so.1.0 ByteFlipperTest.o
	rm -f libByteFlipperTest.so.1; ln -s libByteFlipperTest.so.1.0 libByteFlipperTest.so.1
	rm -f libByteFlipperTest.so; ln -s libByteFlipperTest.so.1 libByteFlipperTest.so
	
clean:
	rm -f *~
	rm -f *.so
	rm -f *.so.*
	rm -f *.o
	rm -f *.class
	rm -f ByteFlipperTest.h