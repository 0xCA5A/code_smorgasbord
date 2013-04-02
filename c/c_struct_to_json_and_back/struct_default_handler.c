#include <stdio.h>
#include <stddef.h>

#include "struct_default_handler.h"



/**
 * @brief helper function to print uniform header
 *
 * @return void
 **/
void print_default_struct_header(const void* p_structure, const char* struct_name_string)
{
    printf("\n################################################################################\n");
    printf("# structure %s @ 0x%p\n", struct_name_string, p_structure);
    printf("################################################################################\n");
}


/**
 * @brief helper function to print uniform footer
 *
 * @return void
 **/
void print_default_struct_footer(void)
{
    printf("################################################################################\n\n");
}


/**
 * @brief helper function to print n spaces
 *
 * @param n number of spaces to print
 * @return void
 **/
void print_n_spaces(int n)
{
    for (int i = 0; i < n; i++)
    {
        printf(" ");
    }
}
