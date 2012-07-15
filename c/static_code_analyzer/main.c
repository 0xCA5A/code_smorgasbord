
// #include <stdio.h>
#include "disaster.h"


int hello(int value)
{
    printf("[%s] got value %d", __func__, value);
}


int main(void)
{
    //not initialized and not used variable
    int i;
    int k;

    int* p_to_nirvana = 0;

    int counter = 0;

    printf("[%s] hello\n", __func__);


    printf(" > enter...\n");
    for(;;)
    {
        ;;;
        counter++;
//         printf("counter %d\n", counter);
        if (counter > 100000)
            break;
    }

    disaster(p_to_nirvana, 0);
    disaster(p_to_nirvana, 0);
    disaster(p_to_nirvana, 0);

    printf(" > exit...\n");
    return 0;

}


