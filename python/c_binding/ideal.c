#include <stdio.h>
#include "ideal.h"


double ideal_pressure_base(ideal_struct in)
{
    return 8.314 * in.moles*in.temp/in.pressure;
}

int main(void)
{
    printf("volume given defualts: %g\n", ideal_pressure());
    printf("volume given boiling temp: %g\n", ideal_pressure(.temp=373.15));
    printf("volume given two moles: %g\n", ideal_pressure(.moles=2));
    printf("volume given two boiling moles: %g\n", ideal_pressure(.moles=2, .temp=373.15));

    return 0;
}