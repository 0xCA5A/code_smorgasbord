#include <stdio.h>
#include "source1.h"

//bad global stuff
int source_one_int_extern;

//this should is limited to this module
static int source_one_int_static = 77;


void say_hello_from_extern_source1(void)
{
    printf("[%s] hello extern!\n", __func__);
    printf(" * pointer to source_one_int_extern: %p\n", &source_one_int_extern);
    printf(" * value of source_one_int_extern: %d\n", source_one_int_extern);
}

void say_hello_from_static_source1(void)
{
    printf("[%s] hello static!\n", __func__);
    printf(" * pointer to source_one_int_static: %p\n", &source_one_int_static);
    printf(" * value of source_one_int_static: %d\n", source_one_int_static);
}