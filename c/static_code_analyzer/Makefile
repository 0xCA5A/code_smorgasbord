

COMPILER_FLAGS=-Wall 



all: gcc clang


gcc:
	gcc ${COMPILER_FLAGS} main.c -o main_gcc.o

clang:
	clang ${COMPILER_FLAGS} main.c -o main_clang.o

scan:
	scan-build gcc main.c


clean:
	rm *.o

