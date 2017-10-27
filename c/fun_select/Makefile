CFLAGS+=-g
APPLICATION_NAME = func_check
HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c)
OBJECTS = $(SOURCES:%.c=%.o)

# CFLAGS+=-DDEBUG

all: $(APPLICATION_NAME)

%.o: %.c %.h
	$(CC) -c $(CFLAGS) $<

$(APPLICATION_NAME): main.c $(OBJECTS) $(HEADERS)
	$(CC) $(CFLAGS) main.c $(OBJECTS) -o $(APPLICATION_NAME) $(LDFLAGS)

.PHONY: clean
clean:
	rm -fv *.o
	rm -fv $(APPLICATION_NAME)
	rm -fv *~
