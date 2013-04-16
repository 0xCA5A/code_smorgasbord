#include <stdio.h>
#include "source1.h"

//extern is here the default...
extern int source_one_int_extern;

static int source_one_int_static = 13;

void say_hello_from_extern_main(void)
{
    printf("[%s] hello extern!\n", __func__);
    printf(" * pointer to source_one_int_extern: %p\n", &source_one_int_extern);
    printf(" * value of source_one_int_extern: %d\n", source_one_int_extern);
}

void say_hello_from_static_main(void)
{
    printf("[%s] hello static!\n", __func__);
    printf(" * pointer to source_one_int_static: %p\n", &source_one_int_static);
    printf(" * value of source_one_int_static: %d\n", source_one_int_static);
}

int main(void)
{
    printf("[%s] hello world\n", __func__);

    say_hello_from_extern_source1();

    //extern linkage
    source_one_int_extern = 42;
    say_hello_from_extern_main();
    say_hello_from_extern_source1();

    //static linkage
    say_hello_from_static_main();
    say_hello_from_static_source1();
    
    return 0;
}


