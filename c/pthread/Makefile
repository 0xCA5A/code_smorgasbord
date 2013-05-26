HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c')
LDFLAGS	= -pthread
CFLAGS	= -std=c99 -Wall -Werror

APPS	= $(SOURCES:%.c=%)


build: $(APPS)

clean:
	rm -f *.o
	rm -f *~
	rm -f $(APPS)