

CC=arm-linux-gnueabihf-gcc
AR=arm-linux-gnueabihf-ar
FLOAT_ABI=-mfloat-abi=hard 


#CC=arm-linux-gnueabi-gcc
#AR=arm-linux-gnueabi-ar
#FLOAT_ABI=-mfloat-abi=softfp



CFLAGS=--no-warn-mismatch -O3 -mcpu=cortex-a8 -mfpu=neon -ftree-vectorize -ffast-math -fsingle-precision-constant ${FLOAT_ABI}

OBJ=main.o
APP=main

LIBOBJ=crap.o megacrap.o
STATIC_LIB=libcrap.a
SHARED_LIB=libcrap.so



all: app_default app_static_object app_shared_object


app_default: ${OBJ} ${LIBOBJ}
	${CC} ${CFLAGS} ${OBJ} ${LIBOBJ} -o ${APP}_default

app_static_object: ${OBJ} static_object
	${CC} ${CFLAGS} -o ${APP}_static_object ${OBJ} libcrap.a

app_shared_object: ${OBJ} shared_object
	${CC} ${CFLAGS} -o ${APP}_shared_object ${OBJ} -L. -lcrap

static_object: ${LIBOBJ}
	${AR} -cvq ${STATIC_LIB} ${LIBOBJ}

shared_object: ${LIBOBJ}
	${CC} ${CFLAGS} -Wall -fPIC -c crap.c megacrap.c
	${CC} ${CFLAGS} -shared -Wl,-soname,libcrap.so.1 -o libcrap.so.1.0 ${LIBOBJ}
	ln -sf ./libcrap.so.1.0 libcrap.so.1
	ln -sf ./libcrap.so.1 libcrap.so



clean:
	rm -fv ${OBJ} ${LIBOBJ}
	rm -fv ${APP}_*
	rm -fv ${SHARED_LIB}*
	rm -fv ${STATIC_LIB}
