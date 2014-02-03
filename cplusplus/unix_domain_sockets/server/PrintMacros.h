#pragma once

#include <iostream>


#define PRINT_FUNCTION_GREETER_MACRO \
    std::cout << "[GREETER] hello from " << __func__ << std::endl;
