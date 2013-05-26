#include <stdio.h>
#include "module1.h"
#include "module2.h"


int main()
{
    global_increment_module1();
    global_increment_module2();

    global_print_module1();
    global_print_module2();

    global_print_address_module1();
    global_print_address_module2();


    return 0;
}
