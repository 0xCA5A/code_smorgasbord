#include <stdio.h>
#include <json/json.h>

#include <stdlib.h>
#include <string.h>



/*printing the value corresponding to boolean, double, integer and strings*/
void print_json_value(json_object *jobj){
  enum json_type type;
  printf("type: ",type);
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
    printf("type: ",type);
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



int main()
{
    FILE* json_fp;
    json_fp = fopen("config.json", "rb");
      if (json_fp == NULL) {
         printf("I couldn't open results.dat for writing.\n");
         exit(0);
      }


    char* string = "{\"sitename\" : \"joys of programming\", \"categories\" : [ \"c\" , [\"c++\" , \"c\" ], \"java\", \"PHP\" ], \"author-details\": { \"admin\": false, \"name\" : \"Joys of Programming\", \"Number of Posts\" : 10 } }";

    char config_string_buffer[2048];
    memset(config_string_buffer, '\0', sizeof(config_string_buffer));
    char* p_config_string_buffer = config_string_buffer;

    do
    {
        *p_config_string_buffer = (char) fgetc(json_fp);

        //filter input data
        //source: man ascii
        //20    SCACE   ' ' (space)
        //09    HT      '\t' (horizontal tab)
        //0A    LF      '\n' (new line)
        //0B    VT      '\v' (vertical tab)
        //0C    FF      '\f' (form feed)
        //0D    CR      '\r' (carriage ret)
        if (*p_config_string_buffer == 0x20 ||
            *p_config_string_buffer == 0x09 ||
            *p_config_string_buffer == 0x0A ||
            *p_config_string_buffer == 0x0B ||
            *p_config_string_buffer == 0x0C ||
            *p_config_string_buffer == 0x0D)
        {
            //ignore this chars
            continue;
        }

        if (*p_config_string_buffer == EOF)
        {
            //overwrite EOF wint \0
            *p_config_string_buffer = '\0';
            break;
        }

//         printf("(p_config_string_buffer - config_string_buffer): %d \n", (p_config_string_buffer - config_string_buffer) );
        p_config_string_buffer++;

    } while((p_config_string_buffer - config_string_buffer) < sizeof(config_string_buffer));

//     printf("string from file: %s\n", config_string_buffer);

    /* close the file */
    fclose(json_fp);

    json_object * jobj_from_file = json_object_from_file("config.json");
    json_parse(jobj_from_file);


    
    
//     printf("JSON string: %s\n", string);
    json_object * jobj = json_tokener_parse(config_string_buffer);
    json_parse(jobj);

    return 0;
}