
#include <stdio.h>
#include "inline_stuff_module.h"
#include "inline_header_stuff.h"

int main(void)
{
    printf("** hello from %s **\n", __func__);

    say_inline_module_hello();
    say_inline_header_hello();
    say_always_inline_hello();
    SAY_MACRO_HELLO();
    say_std_hello();

    printf("done!\n");

    return 0;
}