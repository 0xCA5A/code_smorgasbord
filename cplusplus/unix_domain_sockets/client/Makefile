

OBJ_FILES := UnixDomainSocketClient.o client_application.o
HEADER_FILES := $(wildcard *.hpp *.h)
CPPFLAGS += -Wall -O2
LDFLAGS +=
APP_BINARY := client_application



all: $(OBJ_FILES) $(HEADER_FILES)
	g++ $(CPPFLAGS) -o $(APP_BINARY) $(OBJ_FILES) $(LDFLAGS)

%.o: %.cpp $(HEADER_FILES)
	g++ -c $(CPPFLAGS) -o $@ $<

clean:
	rm -f *.o
	rm -f $(APP_BINARY)
	rm doc/html -rf
