#ifndef PRINTMACROS_HPP
#define PRINTMACROS_HPP


#include <iostream>

#define _PRINT_PREFIX __FILE__ << ":" << __LINE__  << ", " << __PRETTY_FUNCTION__ << " "

#ifdef DEBUG
#define LOG_DEBUG(printString) std::cout << _PRINT_PREFIX << "[DEBUG]: " << printString << std::endl;
#else
#define LOG_DEBUG(printString)
#endif
#define LOG_INFO(printString) std::cout << _PRINT_PREFIX << "[INFO]: " << printString << std::endl;
#define LOG_ERROR(printString) std::cout << _PRINT_PREFIX << "[ERROR]: " << printString << std::endl;


#endif
