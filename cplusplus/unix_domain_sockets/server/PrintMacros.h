#pragma once

#include <iostream>


#define PRINT_FUNCTION_GREETER_MACRO \
    std::cout << "[GREETER] enter function " << __func__ << std::endl;
