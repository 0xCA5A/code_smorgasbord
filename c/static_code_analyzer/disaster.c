
#include "disaster.h"

void disaster(int* p_value, int* p_nothing)
{
    int fail[NUMBER];
    int p_array = malloc(sizeof(fail));
    static int call_counter = 0;

    printf("[%s] hello, i am called. %d call!\n", __func__, call_counter);

    if (0 == 1)
        printf("~~~\n");

    if (0 == 1)
        printf("~~~\n");

    if (0 == 1)
        printf("~~~\n");

    int i;
    //index overrun
    for (i = 0; i < NUMBER + call_counter; i++)
    {
        fail[i] = 21;
        p_array = p_value;
    }

    return call_counter++;

    p_array = 0;
}
