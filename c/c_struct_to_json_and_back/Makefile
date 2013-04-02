

CFLAGS += $(shell pkg-config --cflags json) -std=c99 -Wall
LDFLAGS += $(shell pkg-config --libs json)

CFLAGS += -I/home/sam/projects/github/audiocore-dsp/jack/clients/lib_hard/
CFLAGS += -Dgnu_targets_arm_GCArmv7A

APPLICATION_NAME = c_struct_to_json_and_back

HEADERS = $(shell find . -name '*.h')
SOURCES = $(shell find . -name '*.c' | grep -v main.c | grep -v cunit_hexutils_test.c)
OBJECTS = $(SOURCES:%.c=%.o)



all: $(APPLICATION_NAME)


%.o: %.c %.h
	$(CC) -c $(CFLAGS) $(DEFINES) $<


$(APPLICATION_NAME): main.c $(OBJECTS) $(HEADERS)
	$(CC) $(CFLAGS) main.c $(OBJECTS) -o $(APPLICATION_NAME) $(LDFLAGS) $(DEFINES)


.PHONY: clean
clean:
	rm -fv *.o
	rm -fv $(APPLICATION_NAME)
	rm cunit_*_test
	rm -fv *~


.PHONY: build_tests
build_tests:
	$(CC) $(CFLAGS) $(DEFINES) cunit_hexutils_test.c hexutils.c -o cunit_hexutils_test -lcunit

.PHONY: tests
tests: build_tests
	./cunit_hexutils_test


	