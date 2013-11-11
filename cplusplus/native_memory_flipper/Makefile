OBJ_FILES := main.o
HEADER_FILES := $(wildcard *.hpp)
TEMPLATE_FILES := $(wildcard *.tpp)
CPPFLAGS += -Wall
APP_BINARY := memoryFlipper
COMPILER := g++


all: $(OBJ_FILES) $(HEADER_FILES) $(TEMPLATE_FILES)
	$(COMPILER) $(CPPFLAGS) -o $(APP_BINARY) $(OBJ_FILES)

%.o: %.cpp $(HEADER_FILES) $(TEMPLATE_FILES)
	$(COMPILER) -c $(CPPFLAGS) -o $@ $<

# %.hpp.gch: %.hpp
# 	$(COMPILER) -c $(CPPFLAGS) -o $@ $<

clean:
	rm -f *.o
	rm -f *.gch
	rm -f $(APP_BINARY)
	rm doc/html -rf
	
doxygen:
	cd doc; doxygen Doxyfile; cd -
