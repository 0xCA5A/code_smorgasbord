#include <stdio.h>
#include "hexdump.h"


extern char _binary_my_data_bin_end;
extern unsigned int _binary_my_data_bin_size;
extern char _binary_my_data_bin_start;


int main()
{
    printf("[%s] hello world!\n", __func__);

    char* p_binary_data = &_binary_my_data_bin_start;

    //had to switch to long because of the 32 / 64 bit portability...
    //check sizeof values of different platforms / types for further information
    unsigned long binary_data_size_in_byte = (unsigned long) &_binary_my_data_bin_size;
//     unsigned long binary_data_size_in_byte = (unsigned long) &_binary_my_data_bin_end - (unsigned long) &_binary_my_data_bin_start;

    printf("[%s] data size: %lu byte\n", __func__, binary_data_size_in_byte);
    printf("[%s] data start address: %p\n", __func__, (void*) &_binary_my_data_bin_start);
    printf("[%s] data end address: %p\n", __func__, (void*) &_binary_my_data_bin_end);

    printf("[%s] print binary blob data:", __func__);
    hexdump(p_binary_data, binary_data_size_in_byte);

    printf("[%s] this should be the same output as you get from hexdump...\n", __func__);
    return 0;
}
