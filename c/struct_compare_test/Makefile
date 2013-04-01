CFLAGS += -std=c99 -Wall
LDFLAGS +=

APPLICATION_NAME = struct_compare_test

MAIN_SOURCE_FILE = struct_compare_test.c
SOURCES = $(shell find . -name '*.c' | grep -v $(MAIN_SOURCE_FILE))
HEADERS = $(shell find . -name '*.h')
OBJECTS = $(SOURCES:%.c=%.o)


all: $(APPLICATION_NAME)

%.o: %.c %.h
	$(CC) -c $(CFLAGS) $(DEFINES) $<

$(APPLICATION_NAME): $(MAIN_SOURCE_FILE) $(SOURCES) $(HEADERS) $(OBJECTS)
	$(CC) $(CFLAGS) $(MAIN_SOURCE_FILE) $(OBJECTS) -o $(APPLICATION_NAME) $(LDFLAGS) $(DEFINES)

.PHONY: clean
clean:
	rm -fv *.o
	rm -fv $(APPLICATION_NAME)
	rm -fv *~
