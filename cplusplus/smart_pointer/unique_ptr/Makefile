CC = g++

LDFLAGS =
# DEFINES += -DDEBUG
# DEFINES += -DFUNCTIONTRACE
CPPFLAGS = -Wall -O2 -std=c++0x


HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.cpp' | grep -v main.cpp)
OBJECTS = $(SOURCES:%.cpp=%.o)

APPLICATION_NAME = unique_ptr_test


all: $(APPLICATION_NAME)


%.o: %.cpp %.h
	$(CC) -c $(CPPFLAGS) $(DEFINES) $<


$(APPLICATION_NAME): main.cpp $(OBJECTS) $(HEADERS)
	$(CC) $(CPPFLAGS) main.cpp $(OBJECTS) -o $(APPLICATION_NAME) $(LDFLAGS) $(DEFINES)


.PHONY: clean

clean:
	rm -fv *.o
	rm -fv $(APPLICATION_NAME)
	rm -fv *~
