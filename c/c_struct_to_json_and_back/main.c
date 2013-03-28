#include <stdio.h>
#include <json/json.h>

#include <stdlib.h>
#include <string.h>

#include "main.h"
#include "struct_aerConfig_s_handler.h"



/*printing the value corresponding to boolean, double, integer and strings*/
void print_json_value(json_object *jobj){
  enum json_type type;
  printf("type: ");
  type = json_object_get_type(jobj); /*Getting the type of the json object*/
  switch (type) {
    case json_type_boolean: printf("json_type_boolean\n");
                         printf("value: %s\n", json_object_get_boolean(jobj)? "true": "false");
                         break;
    case json_type_double: printf("json_type_double\n");
                        printf("          value: %lf\n", json_object_get_double(jobj));
                         break;
    case json_type_int: printf("json_type_int\n");
                        printf("          value: %d\n", json_object_get_int(jobj));
                         break;
    case json_type_string: printf("json_type_string\n");
                         printf("          value: %s\n", json_object_get_string(jobj));
                         break;
  }

}

void json_parse_array( json_object *jobj, char *key) {
  void json_parse(json_object * jobj); /*Forward Declaration*/
  enum json_type type;

  json_object *jarray = jobj; /*Simply get the array*/
  if(key) {
    jarray = json_object_object_get(jobj, key); /*Getting the array if it is a key value pair*/
  }

  int arraylen = json_object_array_length(jarray); /*Getting the length of the array*/
  printf("Array Length: %d\n",arraylen);
  int i;
  json_object * jvalue;

  for (i=0; i< arraylen; i++){
    jvalue = json_object_array_get_idx(jarray, i); /*Getting the array element at position i*/
    type = json_object_get_type(jvalue);
    if (type == json_type_array) {
      json_parse_array(jvalue, NULL);
    }
    else if (type != json_type_object) {
      printf("value[%d]: ",i);
      print_json_value(jvalue);
    }
    else {
      json_parse(jvalue);
    }
  }
}

/*Parsing the json object*/
void json_parse(json_object * jobj) {
  enum json_type type;
  json_object_object_foreach(jobj, key, val) { /*Passing through every array element*/
    printf("type: ");
    type = json_object_get_type(val);
    switch (type) {
      case json_type_boolean:
      case json_type_double:
      case json_type_int:
      case json_type_string: print_json_value(val);
                           break;
      case json_type_object: printf("json_type_object\n");
                           jobj = json_object_object_get(jobj, key);
                           json_parse(jobj);
                           break;
      case json_type_array: printf("type: json_type_array, ");
                          json_parse_array(jobj, key);
                          break;
    }
  }
}


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


// http://stackoverflow.com/questions/1557400/hex-to-char-array-in-c
unsigned char hexchar2bin(const char *s)
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
        ret = n + ret*16;
    }
    return ret;
}

/**
 * @brief convert hex string to 32 bit binary. expect the first two chars of the string to be 0x.
 * string has to be \0 terminated, i check the length with strlen function.
 *
 * @param hex_string input string, not modified
 * @param integer_value parsed 32bit bit hex string. first 32bit are converted.
 * @return returns 0 if everything was ok, in the other case a negative integer value.
 **/
int convert_hex_string2bin(const char *hex_string, int* integer_value)
{

    if (hex_string[0] == '0' && (hex_string[1] == 'x' || hex_string[1] == 'X'))
    {
        //seems to be a hex value in a string
        //lets free him! he wants to fit into a single 32bit register!

        //hope this string is terminated by the library...
        int max_string_size = 2 + 32/8 * 2;

//         printf("strnlen(hex_string, max_string_size): %d\n", strnlen(hex_string, max_string_size));

        int string_length = strnlen(hex_string, max_string_size);

        for (int i = string_length - 1; i > 1 ; i--)
        {
            i--;
//             printf("> index: %d\n", i);
//             printf("value converted: 0x%x\n", hexchar2bin(&hex_string[i]));
            int shift = 0;
            shift = (strnlen(hex_string, max_string_size) - 2 - i) / 2 * 8;
//             printf("shift! : %d\n", shift);
            *integer_value |= hexchar2bin(&hex_string[i]) << shift;
//             printf("> integer_value: %x\n", integer_value);
        }
    }
    else
    {
        printf("this should not happen! never!\n");
        return -123;
    }

    printf(">> string value: %s\n", hex_string);
    printf(">> hex value: 0x%x\n", *integer_value);

    return 0;
}


int init_struct_with_json_data(json_object* jobj, void* structure, const char* struct_identifier, const char* type_identifier)
{
    enum json_type type;
    int json_object_index = -1;
    int integer_value = 0;
    const char* string_value;

    json_object_object_foreach(jobj, key, val)
    {
        json_object_index++;

        //first element is structure identifier
        if (json_object_index == 0)
        {
            check_json_data_structure_identifier(key, val, struct_identifier);
            continue;
        }

        //second element is type identifier
        if (json_object_index == 1)
        {
            check_json_data_type_identifier(key, val, type_identifier);
            continue;
        }

        type = json_object_get_type(val);
        switch (type) {
            case json_type_int:
                integer_value = json_object_get_int(val);
                break;
            case json_type_string:
                string_value = json_object_get_string(val);
                if (convert_hex_string2bin(string_value, &integer_value) != 0)
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
                break;
        }

//         printf("key: %s\n", key);
//         if (!strncmp("delay_rx_ag_chg", key, MAX_JSON_FIELD_LENGTH))
//         {
//             printf("set fild delay_rx_ag_chg to value: 0x%x\n", integer_value);
//             ((struct aerConfig_s*)structure)->delay_rx_ag_chg = integer_value;
//         }

        set_struct_aerConfig_s_data(structure, key, integer_value);
    }


/*
typedef enum json_type {
  json_type_null,
  json_type_boolean,
  json_type_double,
  json_type_int,
  json_type_object,
  json_type_array,
  json_type_string
} json_type;*/

        
    
//     printf("key: %s\n", key);
// 
// 
//         printf("value: %s\n", value);
//         printf("struct_type_name: %s\n", struct_type_name);
//     strncmp(value, struct_type_name, MAX_JSON_FIELD_LENGTH) ? printf("struct type name match\n") : printf("struct type name does not match\n");
// 


// /*Parsing the json object*/
// void json_parse(json_object * jobj) {
//   enum json_type type;
//   json_object_object_foreach(jobj, key, val) { /*Passing through every array element*/
//     printf("type: ");
//     type = json_object_get_type(val);
//     switch (type) {
//       case json_type_boolean:
//       case json_type_double:
//       case json_type_int:
//       case json_type_string: print_json_value(val);
//                            break;
//       case json_type_object: printf("json_type_object\n");
//                            jobj = json_object_object_get(jobj, key);
//                            json_parse(jobj);
//                            break;
//       case json_type_array: printf("type: json_type_array, ");
//                           json_parse_array(jobj, key);
//                           break;
//     }
//   }
// }


    
    //second element is the type name 
    

//  for(entry = json_object_get_object(obj)->head; (entry ? (key = (char*)entry->k, val = (struct json_object*)entry->v, entry) : 0); entry = entry->next)


    
//     json_object_object_foreach(jobj, key, val)
//     {
//         printf("type: ",type);
//         type = json_object_get_type(val);
//         switch (type) {
//         case json_type_boolean:
//         case json_type_double:
//         case json_type_int:
//         case json_type_string: print_json_value(val);
//                             break;
//         case json_type_object: printf("json_type_object\n");
//                             jobj = json_object_object_get(jobj, key);
//                             json_parse(jobj);
//                             break;
//         case json_type_array: printf("type: json_type_array, ");
//                             json_parse_array(jobj, key);
//                             break;
//         }
//     }
}



int main(void)
{
//     FILE* json_fp;
//     json_fp = fopen("config.json", "rb");
//     if (json_fp == NULL) {
//         printf("I couldn't open results.dat for writing.\n");
//         exit(0);
//     }

//     char* string = "{\"sitename\" : \"joys of programming\", \"categories\" : [ \"c\" , [\"c++\" , \"c\" ], \"java\", \"PHP\" ], \"author-details\": { \"admin\": false, \"name\" : \"Joys of Programming\", \"Number of Posts\" : 10 } }";

//     char config_string_buffer[2048];
//     memset(config_string_buffer, '\0', sizeof(config_string_buffer));
//     char* p_config_string_buffer = config_string_buffer;
// 
//     do
//     {
//         *p_config_string_buffer = (char) fgetc(json_fp);
// 
//         //filter input data
//         //source: man ascii
//         //20    SCACE   ' ' (space)
//         //09    HT      '\t' (horizontal tab)
//         //0A    LF      '\n' (new line)
//         //0B    VT      '\v' (vertical tab)
//         //0C    FF      '\f' (form feed)
//         //0D    CR      '\r' (carriage ret)
//         if (*p_config_string_buffer == 0x20 ||
//             *p_config_string_buffer == 0x09 ||
//             *p_config_string_buffer == 0x0A ||
//             *p_config_string_buffer == 0x0B ||
//             *p_config_string_buffer == 0x0C ||
//             *p_config_string_buffer == 0x0D)
//         {
//             //ignore this chars
//             continue;
//         }
// 
//         if (*p_config_string_buffer == EOF)
//         {
//             //overwrite EOF wint \0
//             *p_config_string_buffer = '\0';
//             break;
//         }
// 
// //         printf("(p_config_string_buffer - config_string_buffer): %d \n", (p_config_string_buffer - config_string_buffer) );
//         p_config_string_buffer++;
// 
//     } while((p_config_string_buffer - config_string_buffer) < sizeof(config_string_buffer));
// 
// //     printf("string from file: %s\n", config_string_buffer);
// 
//     /* close the file */
//     fclose(json_fp);

    json_object* jobj_from_file = json_object_from_file("aerConfig_t.json");
    if(jobj_from_file == NULL || is_error(jobj_from_file))
    {
        printf("[ERROR] json parser failed to parse data...\n");
        return -1;
    }

    //fixme! this has to match here!
    struct aerConfig_s aerConfig;
    init_struct_with_json_data(jobj_from_file, &aerConfig, "aerConfig_s", "aerConfig_t");


    print_struct_aerConfig_s(&aerConfig);

    
    
//     printf("JSON string: %s\n", string);
//     json_object * jobj = json_tokener_parse(config_string_buffer);
//     json_parse(jobj);

    return 0;
}