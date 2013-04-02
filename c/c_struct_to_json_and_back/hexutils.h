#ifndef __HEXUTILS_H
#define __HEXUTILS_H

#include <stdint.h>

/**
 * @brief function to convert 2 hexchar to binary (real hex)
 * notice that i have to access two chars to build a real hex char (string AA -> 0xAA)
 * original source from here: http://stackoverflow.com/questions/1557400/hex-to-char-array-in-c
 *
 * @param s pointer to the character to convert
 * @return unsigned char, real hex value
 * returns 0 if no conversion possible
 **/
unsigned char hexchar_to_bin(const char *s);

/**
 * @brief convert hex string to 32 bit binary
 * expect the first two chars of the string to be 0x.
 * string has to be \0 terminated, i check the length with strlen function.
 *
 * @param hex_string input string, not modified
 * @param integer_value parsed 32bit bit hex string. first 32bit are converted.
 * @return returns 0 if everything was ok, in the other case a negative integer value.
 **/
int convert_hex_string_to_bin(const char *hex_string, int32_t* integer_value);


#endif
