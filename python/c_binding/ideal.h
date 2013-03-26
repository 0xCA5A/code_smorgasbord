
#ifndef __IDEAL_H
#define __IDEAL_H

typedef struct
{
    double pressure;
    double moles;
    double temp;
} ideal_struct;

#define ideal_pressure(...) ideal_pressure_base((ideal_struct){.pressure=1, \
                                .moles=1, .temp=273.15, __VA_ARGS__})

double ideal_pressure_base(ideal_struct in);


#endif
