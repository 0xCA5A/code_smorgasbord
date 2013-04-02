#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <json/json.h>

#include "main.h"
#include "struct_aerConfig_s_handler.h"
#include "hexutils.h"


//define DEBUG if you want tho have the debug messages
#undef DEBUG
#define DEBUG 1
#include "debug.h"



/**
 * @brief function to init a structure with data from json objects
 * note: in my case there are only int values in the structures.
 * if you have other types, you have to rewrite this!
 *
 * @param jobj_root root element of a JSON object tree
 * @param structure structure to init
 * @return int
 * values != 0: error case
 * value == 0: everything is ok
 **/
int init_struct_with_json_data(json_object* jobj_root,
                               void* structure,
                               int (*p_set_struct_int_data)(void* structure, const char* key, int value)
                              )
{
    enum json_type type;
    int32_t integer_value = 0;
    const char* string_value;

    json_object_object_foreach(jobj_root, key, val)
    {
        //NOTE: all elements are integers in the end!
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

        p_set_struct_int_data(structure, key, integer_value);
    }

    return 0;
}


int main(void)
{
    struct aerConfig_s aerConfig;

    json_object* aerConfig_t_jobj_from_file = json_object_from_file("aerConfig_t.json");
    if(aerConfig_t_jobj_from_file == NULL || is_error(aerConfig_t_jobj_from_file))
    {
        printf("[ERROR @ %s] json parser failed to parse data...\n", __func__);
        return -1;
    }

    int (*p_set_struct_data)(void*, const char*, int) = &set_struct_aerConfig_s_int_data;
    init_struct_with_json_data(aerConfig_t_jobj_from_file, &aerConfig, p_set_struct_data);
    print_struct_aerConfig_s(&aerConfig);

    return 0;
}