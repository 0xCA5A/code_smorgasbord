APPLICATION_NAME = main

all: clean build



clean:
	rm -f *.o
	rm -f ${APPLICATION_NAME}

build:
# 	gcc -c global_definition.c -o global_definition.o
	gcc -c module1.c -o module1.o
	gcc -c module2.c -o module2.o
	gcc main.c module1.o module2.o -o main
