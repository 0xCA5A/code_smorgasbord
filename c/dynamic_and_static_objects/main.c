
#include <stdio.h>

#include "libraryfunction1.h"
#include "libraryfunction2.h"

int main()
{
    int value = -1;

    printf("\nhello world!\n");

    value = libraryfunction1(42);
    printf("value from libraryfunction1(): %d\n", value);

    value = libraryfunction2(42);
    printf("value from libraryfunction2(): %d\n", value);

    printf("done!\n");

    return 0;
}


