
#include <stdio.h>
#include "std_stuff_module.h"

void say_std_hello(void)
{
    printf("hello from %s\n", __func__);
}

