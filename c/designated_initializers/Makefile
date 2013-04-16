

# compile: source1.c source2.c
# 	gcc -c source1.c
# 	gcc -c source2.c


HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c)
OBJECTS = $(SOURCES:%.c=%.o)

APPLICATION_NAME = application


%.o: %.c %.h
	$(CC) -c $(CFLAGS) $(DEFINES) $<

link: $(OBJECTS)
	gcc main.c source1.o source2.o -o $(APPLICATION_NAME)


clean:
	rm -f *.o
	rm -f *~ 
	rm -f $(APPLICATION_NAME)