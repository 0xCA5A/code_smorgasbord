#include <stdio.h>
#include <stddef.h>
#include <string.h>

#include "struct_default_handler.h"
#include "struct_aerConfig_s_handler.h"



//aerConfig_s "reflection" or mapping
struct_field m_struct_aerConfig_s_field_reflection[] = {
{
.field_name = "reset_bitfield",
.field_offset  = offsetof(struct aerConfig_s, reset_bitfield)
},
{
.field_name = "valid_bitfield",
.field_offset  = offsetof(struct aerConfig_s, valid_bitfield)
},
{
.field_name = "y2x_delay",
.field_offset  = offsetof(struct aerConfig_s, y2x_delay)
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
.field_name = "delay_tx_ag_chg",
.field_offset  = offsetof(struct aerConfig_s, delay_tx_ag_chg)
},
{
.field_name = "delay_rx_ag_chg",
.field_offset  = offsetof(struct aerConfig_s, delay_rx_ag_chg)
},
{
.field_name = "num_samp_interp",
.field_offset  = offsetof(struct aerConfig_s, num_samp_interp)
},
{
.field_name = "phone_mode",
.field_offset  = offsetof(struct aerConfig_s, phone_mode)
}
};


/**
 * @brief function to set integer data to all the struct fields
 * assume that the key string is \0 terminated, don't check the boundaries!
 * @param p_aerConfig void pointer to a p_aerConfig structure
 * @param key key as string
 * @param value value as int32_t interger
 * @return int
 * value 0: ok
 * value !0: not ok
 **/
int set_struct_aerConfig_s_int_data(void* p_aerConfig, const char* key, int32_t value)
{
    int i;
    uint8_t found_flag = 0;
    for (i = 0; i < sizeof(m_struct_aerConfig_s_field_reflection) / sizeof(struct_field); i++)
    {
        if (strcmp(m_struct_aerConfig_s_field_reflection[i].field_name, key) == 0)
        {
            *(int *)(p_aerConfig + m_struct_aerConfig_s_field_reflection[i].field_offset) = value;
            found_flag = 1;
            break;
        }

        if (found_flag)
        {
            break;
        }
    }

    if (!found_flag)
    {
        printf("[ERROR @ %s] key not found in reflection, check configuration!\n", __func__);
        return -1;
    }

    return 0;
}


/**
 * @brief function to print the aerConfig_s structure
 *
 * @param p_aerConfig pointer to the struct
 * @return void
 **/
void print_struct_aerConfig_s(struct aerConfig_s* p_aerConfig)
{
    print_default_struct_header(p_aerConfig, "aerConfig_s");

    void* struct_address = (void*) p_aerConfig;
    int32_t int_value;

    for (int i = 0; i < sizeof(m_struct_aerConfig_s_field_reflection) / sizeof(struct_field); i++)
    {
        int_value = *((int32_t *)(((uint8_t*) struct_address) + m_struct_aerConfig_s_field_reflection[i].field_offset));

        printf("# * %s:", m_struct_aerConfig_s_field_reflection[i].field_name);
        print_n_spaces(MAX_SPACES_PER_LINE - strlen(m_struct_aerConfig_s_field_reflection[i].field_name));
        printf("0X%08X, %d\n", int_value, int_value);
    }

    print_default_struct_footer();
}
