#include <stdint.h>
#include <string.h>
#include <stdio.h>

#include "hexutils.h"



/**
 * @brief function to convert 2 hexchar to binary (real hex)
 * notice that i have to access two chars to build a real hex char (string AA -> 0xAA)
 * original source from here: http://stackoverflow.com/questions/1557400/hex-to-char-array-in-c
 *
 * @param s pointer to the character to convert
 * @return unsigned char, real hex value
 * returns 0 if no conversion possible
 **/
unsigned char hexchar_to_bin(const char *s)
{
    int ret = 0;
    int i;
    for(i = 0; i < 2; i++)
    {
        char c = *s++;
        int n = 0;
        if ('0' <= c && c <= '9')
            n = c - '0';
        else if ('a' <=c && c<='f')
            n = 10 + c - 'a';
        else if('A' <=c && c <= 'F')
            n = 10 + c - 'A';
        else
        {
            printf("[ERROR @ %s] unable to convert %c%c to hex\n", __func__, *(s), *(s+1));
            return 0;
        }
        ret = n + ret * 16;
    }
    return ret;
}


/**
 * @brief convert hex string to signed 32 bit integer (int32_t)
 * expect the first two chars of the string to be 0x.
 * string has to be \0 terminated, string length is checked with strlen function.
 *
 * @param hex_string input string, not modified
 * @param integer_value parsed 32bit bit hex string. first 32bit are converted.
 * @return returns 0 if everything was ok, in the other case a negative integer value.
 **/
int convert_hex_string_to_bin(const char *hex_string, int32_t* integer_value)
{
    //expect something like that: 0xABCDEF12
    //maximal string size is 10 chars (8 chars + leading 0x)
    //stringsize between 3 and 10 is allowed.
    const static unsigned int min_string_size_in_chars = 2 + (sizeof(int32_t) * 2) / 8; //3
    const static unsigned int max_string_size_in_chars = 2 + (sizeof(int32_t) * 2) / 1; //10

    int string_length = strlen(hex_string);
    if (string_length < min_string_size_in_chars || string_length > max_string_size_in_chars)
    {
        printf("[ERROR @ %s] input string length (%d) is not valid\n", __func__, string_length);
        return -1;
    }

    //check if string formatting is as expected...
    if (!(hex_string[0] == '0' && (hex_string[1] == 'x' || hex_string[1] == 'X')))
    {
        printf("[ERROR @ %s] hex string identifier (leading 0x) not found in string %s...\n", __func__, hex_string);
        return -1;
    }

    //seems to be a hex value in a string
    //lets free him! he wants to fit into a single 32bit register!
    char my_string_buffer[max_string_size_in_chars];
    memcpy(my_string_buffer, hex_string, string_length);
    if (string_length % 2 != 0 )
    {
        //replace the x from 0x... with a '0',
        //have to provide a even number of characters to get a clean conversion
        my_string_buffer[1] = '0';
    }

    *integer_value = 0x00000000;
    int shift = 0;
    for (int i = string_length - 1; i > 1 ; i--)
    {
        i--;
        shift = (string_length - 2 - i) / 2 * 8;
        *integer_value |= hexchar_to_bin(&my_string_buffer[i]) << shift;
    }

    return 0;
}
