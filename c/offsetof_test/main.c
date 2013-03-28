#include <stdio.h>
#include <stddef.h>


// man offsetof for further information


typedef struct first_struct_s
{
    unsigned int width;
    unsigned int height;
} first_struct_t;


typedef struct second_struct_s
{
    first_struct_t;
    unsigned int weight;
    unsigned char color_value_r;
    unsigned char color_value_g;
    unsigned char color_value_b;
} second_struct_t;


int main()
{
    long offset_in_bytes;

    printf("[%s] start!\n", __func__);
    second_struct_t my_second_struct;

    //NOTE: direct access is possible with c11
//     my_second_struct.width = 123;
//     my_second_struct.height = 123;
    my_second_struct.weight = 123;

    //NOTE: direct access is possible with c11
//     printf("my_second_struct.width: %d\n", my_second_struct.width);
//     printf("my_second_struct.height: %d\n", my_second_struct.height);
    printf("my_second_struct.weight: %d\n", my_second_struct.weight);

    printf("\n");
    printf(" * sizeof(my_second_struct): %ld [byte]\n",  sizeof(my_second_struct));

    offset_in_bytes = offsetof(struct second_struct_s, weight);
    printf(" * offsetof(struct second_struct_s, weight): %ld [byte]\n", offset_in_bytes);

    offset_in_bytes = offsetof(struct second_struct_s, non_existing_stuff);
    printf(" * offsetof(struct second_struct_s, non_existing_stuff): %ld [byte]\n", offset_in_bytes);

    return 0;
}

