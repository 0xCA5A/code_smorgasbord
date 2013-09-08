
CXXFLAGS += -std=c++11 -pthread -Wall -Werror
APPLICATION_NAME = application

all: calculator.o main.o
	g++ ${CXXFLAGS} calculator.o main.o -o ${APPLICATION_NAME}

%.o: %.cpp
	g++ $(CXXFLAGS) -c -o $@ $<

clean:
	rm -f *.o
	rm -f *~