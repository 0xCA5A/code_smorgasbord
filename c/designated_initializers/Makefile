HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c)
OBJECTS = $(SOURCES:%.c=%.o)
CFLAGS = -O2

APPLICATION_NAME = application


%.o: %.c %.h
	$(CC) -c $(CFLAGS) $(DEFINES) $<

link: $(OBJECTS)
	gcc $(CFLAGS) main.c -o $(APPLICATION_NAME)


clean:
	rm -f *.o
	rm -f *~ 
	rm -f $(APPLICATION_NAME)