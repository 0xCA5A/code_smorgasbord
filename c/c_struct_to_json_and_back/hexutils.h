#ifndef __HEXUTILS_H
#define __HEXUTILS_H


#include <stdint.h>

unsigned char hexchar_to_bin(const char *s);
int convert_hex_string_to_bin(const char *hex_string, int32_t* integer_value);


#endif
