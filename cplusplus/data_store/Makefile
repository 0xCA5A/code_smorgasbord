OBJ_FILES := DataStore.o DataStoreTestManager.o
HEADER_FILES := $(wildcard *.hpp)
TEMPLATE_FILES := $(wildcard *.tpp)
CPPFLAGS += -Wall -std=c++11
# CPPFLAGS += -DDEBUG
APP_BINARY := DataStore
COMPILER := g++


all: $(OBJ_FILES) $(HEADER_FILES) $(TEMPLATE_FILES)
	$(COMPILER) $(CPPFLAGS) -o $(APP_BINARY) $(OBJ_FILES)

%.o: %.cpp $(HEADER_FILES) $(TEMPLATE_FILES)
	$(COMPILER) -c $(CPPFLAGS) -o $@ $<

# %.hpp.gch: %.hpp
# 	$(COMPILER) -c $(CPPFLAGS) -o $@ $<

clean:
	rm -f *.*~
	rm -f *.o
	rm -f *.gch
	rm -f $(APP_BINARY)
	rm doc/html -rf
	rm -rf build

doxygen:
	cd doc; doxygen Doxyfile; cd -

indent:
	indent -kr -ci2 -cli2 -i2 -nut *.[cth]pp
