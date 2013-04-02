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


//TODO: remove this if possible
int check_json_data_structure_identifier(char* key, struct json_object* value, const char* struct_identifier)
{
    enum json_type type = json_object_get_type(value);

    //check key
    if (!strcmp(key, "struct_identifier"))
    {
        DEBUG_PRINT("[%s] key is struct_identifier as expected...\n", __func__);
    }
    else
    {
        printf("[ERROR @ %s] key is _not_ struct_identifier...\n", __func__);
        return -1;
    }

    //check type
    if (type == json_type_string)
    {
        DEBUG_PRINT("[%s] type is json_type_string as expected...\n",__func__);
    }
    else
    {
        printf("[ERROR @ %s] type of struct_identifier is not string\n", __func__);
        return -2;
    }

    //check value
    if (!strncmp(struct_identifier, json_object_get_string(value), MAX_JSON_FIELD_LENGTH))
    {
        DEBUG_PRINT("[%s] value is %s as expected...\n",__func__, struct_identifier);
    }
    else
    {
        printf("[ERROR @ %s] value is _not_ %s...\n", __func__, struct_identifier);
        return -3;
    }

    return 0;
}


//TODO: remove this if possible
//TODO: this is the same crap as check_json_data_structure_identifier, fix this!!!
int check_json_data_type_identifier(char* key, struct json_object* value, const char* type_identifier)
{
    enum json_type type = json_object_get_type(value);

    //check key
    if (!strcmp(key, "type_identifier"))
    {
        DEBUG_PRINT("[%s] key is type_identifier as expected...\n", __func__);
    }
    else
    {
        printf("[ERROR @ %s] key is _not_ type_identifier...\n", __func__);
        return -1;
    }

    //check type
    if (type == json_type_string)
    {
        DEBUG_PRINT("[%s] type is json_type_string as expected...\n",__func__);
    }
    else
    {
        printf("[ERROR @ %s] type of type_identifier is not string\n", __func__);
        return -2;
    }

    //check value
    if (!strncmp(type_identifier, json_object_get_string(value), MAX_JSON_FIELD_LENGTH))
    {
        DEBUG_PRINT("[%s] value is %s as expected...\n",__func__, type_identifier);
    }
    else
    {
        printf("[ERROR @ %s] value is _not_ %s...\n", __func__, type_identifier);
        return -3;
    }

    return 0;
}


/**
 * @brief function to init a structure with data from json objects
 * note: in my case there are only int values in the structures.
 * if you have other types, you have to rewrite this!
 *
 * @param jobj_root root element of a JSON object tree
 * @param structure structure to init
 * @param struct_identifier structure identifier string (struct name)
 * @param type_identifier type identifier string (typedef name)
 * @return int
 * values != 0: error case
 * value == 0: everything is ok
 **/
int init_struct_with_json_data(json_object* jobj_root,
                               void* structure,
                               const char* struct_identifier,
                               const char* type_identifier,
                               int (*p_set_struct_int_data)(void* structure, const char* key, int value)
                              )
{
    enum json_type type;
    int32_t integer_value = 0;
    const char* string_value;

    int json_object_index = -1;
    json_object_object_foreach(jobj_root, key, val)
    {
        json_object_index++;

        //first element is structure identifier, decide if we need that...
        //TODO: remove this
        if (json_object_index == 0)
        {
            check_json_data_structure_identifier(key, val, struct_identifier);
            continue;
        }

        //second element is type identifier, decide if we need that...
        //TODO: remove this
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

        p_set_struct_int_data(structure, key, integer_value);
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

    //int values only in this struct
    int (*p_set_struct_data)(void*, const char*, int) = &set_struct_aerConfig_s_int_data;
    init_struct_with_json_data(jobj_from_file, &aerConfig, "aerConfig_s", "aerConfig_t", p_set_struct_data);
    print_struct_aerConfig_s(&aerConfig);

    return 0;
}