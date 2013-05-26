#include <stdio.h>
#include "global_definition.h"


void global_increment_module2(void)
{
    printf("[%s] increment global_value...\n", __func__);
    global_value++;
}

void global_print_address_module2(void)
{
    printf("[%s] global_value address: %p\n", __func__, &global_value);
}

void global_print_module2(void)
{
    printf("[%s] global_value is %d\n", __func__, global_value);
}