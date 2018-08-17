
LIB_SRC_FILES = libraryfunction1.c libraryfunction2.c
LIB_HDR_FILES = libraryfunction1.h libraryfunction2.h
SRC_FILES = main.c 


STATIC_LIB_NAME = libtest.a
DYNAMIC_LIB_NAME = libtest.so

STATIC_BIN_NAME = static.o
DYNAMIC_BIN_NAME = dynamic.o

DYNAMIC_LIB_MAJOR = 1
DYNAMIC_LIB_MINOR = 0

CC = gcc
CFLAGS += -W -Wall -Werror -std=c99
LDFLAGS += -lm
LS = ls -has


################################################################################
# help function
################################################################################
HELP = @grep -B1 '^[a-zA-Z][^:]*:' Makefile |\
	awk 'function p(h,t){printf"%-15s=%s\n",h,t};\
	/\#+/{t=$$0};\
	/:/{gsub(":.*","");h=$$0};\
	/--/{p(h,t);t=h="";};\
	END{p(h,t)}' |\
	sed -n 's/=.*\#+/:/gp'

#+ prints this list
help: 
	@echo
	@echo ---------------------------------------------------------------------
	@echo $@ menu
	@echo ---------------------------------------------------------------------
	$(HELP)
	@echo ---------------------------------------------------------------------
################################################################################


#+ compile source file
%.o: %.c
	@echo ---------------------------------------------------------------------
	@echo compiling source file $< to $@
	@echo ---------------------------------------------------------------------

	$(CC) -c $(CFLAGS) -o $@ $< -fPIC

	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------


#+ build program dynamic
dynamic : $(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR).$(DYNAMIC_LIB_MINOR) main.c
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------

	$(CC) $(CFLAGS) main.c -o $(DYNAMIC_BIN_NAME) -L./ -ltest $(LDFLAGS)
	LD_LIBRARY_PATH=. ldd $(DYNAMIC_BIN_NAME)

	@echo "\ndynamic build output:"
	$(LS) -halt $(DYNAMIC_BIN_NAME)

	@echo "\nnumber of function symbols in binary:"
	readelf -a $(DYNAMIC_BIN_NAME) | grep FUNC | grep -v __ | wc -l

	strip $(DYNAMIC_BIN_NAME)

	@echo "\ndynamic build output after strip:"
	$(LS) -halt $(DYNAMIC_BIN_NAME)

	@echo "\nnumber of function symbols in binary after strip:"
	readelf -a $(DYNAMIC_BIN_NAME) | grep FUNC | grep -v __ | wc -l

	@echo "\nrun dynamic bin..."
	LD_LIBRARY_PATH=. ./$(DYNAMIC_BIN_NAME)
	
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------


#+ build libs, dynamic linking
$(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR).$(DYNAMIC_LIB_MINOR) : libraryfunction1.o libraryfunction2.o
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------

	$(CC) -fPIC -shared $(CFLAGS) -o $(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR).$(DYNAMIC_LIB_MINOR) lib*.o

	ln -s $(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR).$(DYNAMIC_LIB_MINOR) $(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR)
	ln -s $(DYNAMIC_LIB_NAME).$(DYNAMIC_LIB_MAJOR) $(DYNAMIC_LIB_NAME)

	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------


#+ build program static
static : $(STATIC_LIB_NAME) main.c
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------

	$(CC) $(CFLAGS) -static main.c $(STATIC_LIB_NAME) -o $(STATIC_BIN_NAME) $(LDFLAGS)

	@echo "\nstatic build output:"
	$(LS) -halt $(STATIC_BIN_NAME)

	@echo "\nnumber of function symbols in binary:"
	readelf -a $(STATIC_BIN_NAME) | grep FUNC | grep -v __ | wc -l

	strip $(STATIC_BIN_NAME)

	@echo "\nstatic build output after strip:"
	$(LS) -halt $(STATIC_BIN_NAME)

	@echo "\nnumber of function symbols in binary after strip:"
	readelf -a $(STATIC_BIN_NAME) | grep FUNC | grep -v __ | wc -l

	@echo "\nrun static bin..."
	./$(STATIC_BIN_NAME)

	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------


#+ build libs, static linking
$(STATIC_LIB_NAME) : libraryfunction1.o libraryfunction2.o 
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------

	ar -cvq $(STATIC_LIB_NAME) lib*.o

	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------


#+ clean project
clean :
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------

	touch clean.o clean.a clean.so
	rm *.o *.a *.so*

	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo --------------------------------------------------------------------- 
