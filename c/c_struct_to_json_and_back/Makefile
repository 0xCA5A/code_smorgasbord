

CFLAGS += $(shell pkg-config --cflags json) -std=c99 -Wall
LDFLAGS += $(shell pkg-config --libs json)

all:
	cc main.c -o main.o $(CFLAGS) $(LDFLAGS)

clean:
	rm -f *.o


