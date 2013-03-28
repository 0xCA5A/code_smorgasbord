#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <json/json.h>

#include "main.h"
#include "struct_aerConfig_s_handler.h"


//define DEBUG if you want tho have the debug messages
#undef DEBUG
#define DEBUG 1
#include "debug.h"


int check_json_data_structure_identifier(char* key, struct json_object* value, const char* struct_identifier)
{
    enum json_type type = json_object_get_type(value);

    //check key
    if (!strcmp(key, "struct_identifier"))
    {
        printf("[%s] key is struct_identifier as expected...\n", __func__);
    }
    else
    {
        printf("[ERROR @ %s] key is _not_ struct_identifier...\n", __func__);
        return -1;
    }

    //check type
    if (type == json_type_string)
    {
        printf("[%s] type is json_type_string as expected...\n",__func__);
    }
    else
    {
        printf("[ERROR @ %s] type of struct_identifier is not string\n", __func__);
        return -2;
    }

    //check value
    if (!strncmp(struct_identifier, json_object_get_string(value), MAX_JSON_FIELD_LENGTH))
    {
        printf("[%s] value is %s as expected...\n",__func__, struct_identifier);
    }
    else
    {
        printf("[ERROR @ %s] value is _not_ %s...\n", __func__, struct_identifier);
        return -3;
    }

    return 0;
}


//TODO: this is the same crap as check_json_data_structure_identifier, fix this!!!
int check_json_data_type_identifier(char* key, struct json_object* value, const char* type_identifier)
{
    enum json_type type = json_object_get_type(value);

    //check key
    if (!strcmp(key, "type_identifier"))
    {
        printf("[%s] key is type_identifier as expected...\n", __func__);
    }
    else
    {
        printf("[ERROR @ %s] key is _not_ type_identifier...\n", __func__);
        return -1;
    }

    //check type
    if (type == json_type_string)
    {
        printf("[%s] type is json_type_string as expected...\n",__func__);
    }
    else
    {
        printf("[ERROR @ %s] type of type_identifier is not string\n", __func__);
        return -2;
    }

    //check value
    if (!strncmp(type_identifier, json_object_get_string(value), MAX_JSON_FIELD_LENGTH))
    {
        printf("[%s] value is %s as expected...\n",__func__, type_identifier);
    }
    else
    {
        printf("[ERROR @ %s] value is _not_ %s...\n", __func__, type_identifier);
        return -3;
    }

    return 0;
}



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
    int ret=0;
    int i;
    for(i=0; i<2; i++)
    {
        char c = *s++;
        int n=0;
        if( '0'<=c && c<='9' )
            n = c-'0';
        else if( 'a'<=c && c<='f' )
            n = 10 + c-'a';
        else if( 'A'<=c && c<='F' )
            n = 10 + c-'A';
        else
        {
            printf("[ERROR @ %s] unable to convert %c%c to hex\n", __func__, *(s), *(s+1));
            return 0;
        }
        ret = n + ret*16;
    }
    return ret;
}

/**
 * @brief convert hex string to 32 bit binary
 * expect the first two chars of the string to be 0x.
 * string has to be \0 terminated, i check the length with strlen function.
 *
 * @param hex_string input string, not modified
 * @param integer_value parsed 32bit bit hex string. first 32bit are converted.
 * @return returns 0 if everything was ok, in the other case a negative integer value.
 **/
int convert_hex_string_to_bin(const char *hex_string, int32_t* integer_value)
{
    //expect something like that: 0xABCDEF12
    //maximal string size is 10 chars
    //stringsize between 3 and 10 is allowed.
    int max_string_size = 2 + 32/8 * 2;
    int min_string_size = 2 + 8/8 * 2;
    int string_length = strnlen(hex_string, max_string_size);
    if (string_length < min_string_size || string_length > max_string_size)
    {
        printf("[ERROR @ %s] input string size is not ok\n", __func__);
        return -1;
    }

    if (hex_string[0] == '0' && (hex_string[1] == 'x' || hex_string[1] == 'X'))
    {
        //seems to be a hex value in a string
        //lets free him! he wants to fit into a single 32bit register!

        *integer_value = 0;
        int shift = 0;
        for (int i = string_length - 1; i > 1 ; i--)
        {
            i--;
//             printf("> index: %d\n", i);
//             printf("value converted: 0x%x\n", hexchar_to_bin(&hex_string[i]));

            shift = (string_length - 2 - i) / 2 * 8;
//             printf("shift! : %d\n", shift);
            *integer_value |= hexchar_to_bin(&hex_string[i]) << shift;
//             printf("> integer_value: %x\n", *integer_value);
        }
    }
    else
    {
        printf("[ERROR @ %s] hex string identifier not found in string (leading 0x)...\n", __func__);
        return -1;
    }

//     printf(">> string value: %s\n", hex_string);
//     printf(">> hex value: 0x%x\n", *integer_value);

    return 0;
}


/**
 * @brief function to init a structure with data from a json object
 *
 * @param jobj ...
 * @param structure ...
 * @param struct_identifier ...
 * @param type_identifier ...
 * @return int
 **/
int init_struct_with_json_data(json_object* jobj,
                               void* structure,
                               const char* struct_identifier,
                               const char* type_identifier,
                               int (*p_set_struct_data)(void* structure, const char* key, int value)
                              )
{
    enum json_type type;
    int32_t integer_value = 0;
    const char* string_value;

    int json_object_index = -1;
    json_object_object_foreach(jobj, key, val)
    {
        json_object_index++;

        //first element is structure identifier, decide if we need that...
        if (json_object_index == 0)
        {
            check_json_data_structure_identifier(key, val, struct_identifier);
            continue;
        }

        //second element is type identifier, decide if we need that...
        if (json_object_index == 1)
        {
            check_json_data_type_identifier(key, val, type_identifier);
            continue;
        }

        //all the other elements...
        type = json_object_get_type(val);
        switch (type) {
            case json_type_int:
                integer_value = json_object_get_int(val);
                break;
            case json_type_string:
                string_value = json_object_get_string(val);
                if (convert_hex_string_to_bin(string_value, &integer_value) != 0)
                {
                    printf("[ERROR @ %s] unable to convert hex string to real hex... string was: %s\n", __func__, string_value);
                    return -2;
                }
                break;
            case json_type_null:
            case json_type_boolean:
            case json_type_double:
            case json_type_object:
            case json_type_array:
                printf("[ERROR @ %s] uuuh, tried to parse a json value and it is _not_ string or int...\n", __func__);
                return -1;
        }

        p_set_struct_data(structure, key, integer_value);
    }

    return 0;
}


int main(void)
{
    struct aerConfig_s aerConfig;

    json_object* jobj_from_file = json_object_from_file("aerConfig_t.json");
    if(jobj_from_file == NULL || is_error(jobj_from_file))
    {
        printf("[ERROR @ %s] json parser failed to parse data...\n", __func__);
        return -1;
    }

    int (*p_set_struct_data)(void*, const char*, int) = &set_struct_aerConfig_s_int_data;
    init_struct_with_json_data(jobj_from_file, &aerConfig, "aerConfig_s", "aerConfig_t", p_set_struct_data);
    print_struct_aerConfig_s(&aerConfig);

    return 0;
}