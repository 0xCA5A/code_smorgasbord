HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c)
OBJECTS = $(SOURCES:%.c=%.o)

APPLICATION_NAME = binblobaccess
BIN_DATA_NAME = my_data.bin
OBJECT_DATA_NAME = my_data.obj
DATA_SIZE_IN_BYTE = 42

#check the objcopy output parameter (elf32-i386)
CFLAGS = -m32


%.o: %.c %.h
	@echo "################################################################################"
	@echo "# $@"
	@echo "################################################################################"
	$(CC) -c $(CFLAGS) $(DEFINES) $<


link: $(OBJECTS) blob_obj
	@echo "################################################################################"
	@echo "# $@"
	@echo "################################################################################"
	$(CC) $(CFLAGS) main.c $(OBJECTS) $(OBJECT_DATA_NAME) -o $(APPLICATION_NAME)


blob:
	@echo "################################################################################"
	@echo "# $@"
	@echo "################################################################################"
	dd if=/dev/urandom of=$(BIN_DATA_NAME) bs=1 count=$(DATA_SIZE_IN_BYTE)
	hexdump -C $(BIN_DATA_NAME)


blob_obj: blob
	@echo "################################################################################"
	@echo "# $@"
	@echo "################################################################################"
	objcopy --input binary --output elf32-i386 --binary-architecture i386 $(BIN_DATA_NAME) $(OBJECT_DATA_NAME)
	nm $(OBJECT_DATA_NAME)


clean:
	@echo "################################################################################"
	@echo "# $@"
	@echo "################################################################################"
	rm -f *.o
	rm -f *~ 
	rm -f $(APPLICATION_NAME)
	rm -f $(BIN_DATA_NAME) $(OBJECT_DATA_NAME)