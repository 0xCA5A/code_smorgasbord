#include <stdio.h>
#include <time.h>
#include <stdlib.h>


// macro to define static variable with initialization
// source: book 21st century c
#define static_def(type, var, initialization) \
    static type var = 0; \
    if (!(var)) var = (initialization);


int get_random_int()
{
    srand(time(NULL));
    return rand();
}


int main(void)
{
    printf("hello\n");

    static_def(int, my_int, 256);
    static_def(int, my_int_from_function, get_random_int());
    static_def(float, my_float, 123.123);

    printf(" -> static int number: %d\n", my_int);
    printf(" -> static int number from function: %d\n", my_int_from_function);
    printf(" -> static float number: %f\n", my_float);

    return 0;
}



