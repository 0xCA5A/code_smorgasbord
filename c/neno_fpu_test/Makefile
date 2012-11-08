SHELL := /bin/bash


DEFAULT_OPT_FLAGS=

MEDIUM_OPT_FLAGS=-O2 -pipe\
 -mcpu=cortex-a8 -mtune=cortex-a8 -mfpu=neon

FULL_OPT_FLAGS=-O3 -pipe\
-mcpu=cortex-a8 -mtune=cortex-a8 -mfpu=neon \
-ftree-vectorize -mfloat-abi=hard \
-ffast-math -fsingle-precision-constant 
#-fno-inline-functions


all: default medium_optimized full_optimized 
	strip *.o

	@echo "\nfile info:"
	file *.o
	file *.asm
	
	@echo "\nnr of lines in asm files:"
	wc -l *.asm

	@echo "\nobject files:"
	ls -alt *.o

	@echo "\nrun time:"
	for obj in $$(ls *.o); do echo "run $$obj": ; time ./$${obj} 1>/dev/null; done;


default:
	gcc hello.c -S -o default.asm ${DEFAULT_OPT_FLAGS}
	gcc hello.c -o default.o ${DEFAULT_OPT_FLAGS}

medium_optimized:
	gcc hello.c -S -o medium_optimized.asm ${MEDIUM_OPT_FLAGS}
	gcc hello.c -o medium_optimized.o ${MEDIUM_OPT_FLAGS}

full_optimized:
	gcc hello.c -S -o full_optimized.asm ${FULL_OPT_FLAGS}
	gcc hello.c -o full_optimized.o ${FULL_OPT_FLAGS}

clean:
	rm -f *.o *.asm

