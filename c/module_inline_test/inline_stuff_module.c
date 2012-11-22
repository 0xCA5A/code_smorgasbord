
#include <stdio.h>
#include "inline_stuff_module.h"

inline void say_inline_module_hello(void)
{
    printf("hello from %s\n", __func__);
}

inline __attribute__((always_inline)) void say_always_inline_hello(void)
{
    printf("hello from %s\n", __func__);
}