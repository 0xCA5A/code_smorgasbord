CFLAGS += -std=c99 -Wall
LDFLAGS +=

APPLICATION_NAME = offsetof_test

HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c)
OBJECTS = $(SOURCES:%.c=%.o)


all: $(APPLICATION_NAME)

%.o: %.c %.h
	$(CC) -c $(CFLAGS) $(DEFINES) $<

$(APPLICATION_NAME): main.c $(OBJECTS) $(HEADERS)
	$(CC) $(CFLAGS) main.c $(OBJECTS) -o $(APPLICATION_NAME) $(LDFLAGS) $(DEFINES)
	
.PHONY: clean
clean:
	rm -fv *.o
	rm -fv $(APPLICATION_NAME)
	rm -fv *~
