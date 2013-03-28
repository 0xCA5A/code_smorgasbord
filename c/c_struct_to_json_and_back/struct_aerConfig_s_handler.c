

#include <stdio.h>
#include <stddef.h>
#include <string.h>

#include "struct_aerConfig_s_handler.h"


void print_struct_header(const void* p_structure, const char* struct_name)
{
    printf("################################################################################\n");
    printf("# structure %s @ 0x%p\n", struct_name, p_structure);
    printf("################################################################################\n");
}


void print_struct_footer(void)
{
    printf("################################################################################\n");
}




struct_field m_struct_field_list[] = {
{
.field_name = "delay_rx_ag_chg",
.field_offset  = offsetof(struct aerConfig_s, delay_rx_ag_chg)
},
{
.field_name = "delay_tx_ag_chg",
.field_offset  = offsetof(struct aerConfig_s, delay_tx_ag_chg)
},
{
.field_name = "num_samp_interp",
.field_offset  = offsetof(struct aerConfig_s, num_samp_interp)
},
{
.field_name = "phone_mode",
.field_offset  = offsetof(struct aerConfig_s, phone_mode)
},
{
.field_name = "reset_bitfield",
.field_offset  = offsetof(struct aerConfig_s, reset_bitfield)
},
{
.field_name = "srate_bitfield",
.field_offset  = offsetof(struct aerConfig_s, srate_bitfield)
},
{
.field_name = "tail_length",
.field_offset  = offsetof(struct aerConfig_s, tail_length)
},
{
.field_name = "valid_bitfield",
.field_offset  = offsetof(struct aerConfig_s, valid_bitfield)
},
{
.field_name = "y2x_delay",
.field_offset  = offsetof(struct aerConfig_s, y2x_delay)
}
};

// struct struct_field* get_struct_pointer()
// {
//     return &struct_field_list;
// }


//strings are teminated!
struct struct_field* set_struct_aerConfig_s_data(void* p_aerConfig, const char* key, int value)
{
    void* structure = (void*) p_aerConfig;
    int i;
    for (i = 0; i < sizeof(m_struct_field_list) / sizeof(struct_field); i++)
    {
        if (strcmp(m_struct_field_list[i].field_name, key) == 0)
        {
//             printf("element found! -> %s\n ", m_struct_field_list[i]);
            *(int *)(structure + m_struct_field_list[i].field_offset) = value;
        }
    }
}



void print_struct_aerConfig_s(struct aerConfig_s* p_aerConfig)
{
    print_struct_header(p_aerConfig, "aerConfig_s");

//     printf("* delay_rx_ag_chg:\t      0x%x\n", p_aerConfig->delay_rx_ag_chg);
//     printf("* delay_tx_ag_chg:\t      0x%x\n", p_aerConfig->delay_tx_ag_chg);
//     printf("* num_samp_interp:\t      0x%x\n", p_aerConfig->num_samp_interp);
//     printf("* phone_mode:\t\t         0x%x\n", p_aerConfig->phone_mode);
//     printf("* reset_bitfield:\t       0x%x\n", p_aerConfig->reset_bitfield);
//     printf("* srate_bitfield:\t       0x%x\n", p_aerConfig->srate_bitfield);
//     printf("* tail_length:\t\t        0x%x\n", p_aerConfig->tail_length);
//     printf("* valid_bitfield:\t       0x%x\n", p_aerConfig->valid_bitfield);
//     printf("* y2x_delay:\t\t          0x%x\n", p_aerConfig->y2x_delay);
// 


    void* struct_address = (void*) p_aerConfig;

    int32_t int_value;

//     printf("NEW nr of elements!! %d\n", sizeof(m_struct_field_list) / sizeof(struct_field));
    int i;

    char my_space[MAX_SPACE_IN_BYTE];

    for (i = 0; i < sizeof(m_struct_field_list) / sizeof(struct_field); i++)
    {
        int_value = *((int32_t *)(((uint8_t*) struct_address) + m_struct_field_list[i].field_offset));

        memset(my_space, ' ', MAX_SPACE_IN_BYTE);
        my_space[MAX_SPACE_IN_BYTE - 1 - strlen(m_struct_field_list[i].field_name)] = '\0';

        printf("* %s:", m_struct_field_list[i].field_name);
        printf("%s", my_space);
        printf("0x%08x, %d\n", int_value, int_value);

    }

    print_struct_footer();
}

/*


void init_list()
{
//     g_struct_field_list[0] =  {.field_name = "hello", .field_offset = 123, .field_size = 4};
}*/
/*
void set_value(const char* key, int value)
{

//     for()
    

    
}*/
