#include <stdio.h>
#include <string.h>

#include "hexdump.h"


typedef struct my_data
{
    int hello;
    long world;
    char pain;
    long* p_int_array;
} my_data_t;


void print_data_type_size()
{
    printf("[i] sizes on your architecture:\n");
    printf("sizeof(char):\t\t%lu\n", sizeof(char));
    printf("sizeof(short):\t\t%lu\n", sizeof(short));
    printf("sizeof(int):\t\t%lu\n", sizeof(int));
    printf("sizeof(long):\t\t%lu\n", sizeof(long));
    printf("\n");
    printf("sizeof(void*):\t\t%lu\n", sizeof(void*));
    printf("sizeof(char*):\t\t%lu\n", sizeof(char*));
    printf("sizeof(short*):\t\t%lu\n", sizeof(short*));
    printf("sizeof(int*):\t\t%lu\n", sizeof(int*));
    printf("sizeof(long*):\t\t%lu\n", sizeof(long*));
    printf("\n");
    printf("sizeof(my_data_t):\t%lu\n", sizeof(my_data_t));
    printf("\n");
}


int main(void)
{
    print_data_type_size();

    my_data_t data0 = {.hello = 0xABCDEF12, .world = 0x0123456789ABCDEF, .pain = 0xCC, .p_int_array = (long[]){4,5,6}};
    printf("[i] data0 memory:");
    hexdump(&data0, sizeof(data0));

    my_data_t data1;
    memset(&data1, 0x42, sizeof(data1));
    printf("[i] data1 memory:");
    hexdump(&data1, sizeof(data1));

    printf("[i] assign data0 to data1...\n");
    data1 = data0;

    printf("[i] compare data, byte by byte...\n");
    if (!memcmp(&data0, &data1, sizeof(my_data_t)))
    {
        printf(" * data does match!\n");
    }
    else
    {
        printf(" * data does _not_ match!\n");
    }

    printf("\n\n");

    printf("[i] data0 memory:");
    hexdump(&data0, sizeof(data0));

    printf("\n");

    printf("[i] data1 memory:");
    hexdump(&data1, sizeof(data1));

    return 0;
}



