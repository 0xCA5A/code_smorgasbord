#include <stdio.h>
#include "Crap.h"


int main()
{
    int value;

    printf("[main] Crap dummy variable\n");
    Crap dummy(12);

    printf("[main] call Crap crap\n");
    {
        Crap crap;
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }

    printf("[main] call Crap crap()\n");
    {
        Crap crap();
        printf("\t[i] does not work, compiler complains!\n");
        //main.cpp:22:14: error: request for member ‘getValue’ in ‘crap’, which is of non-class type ‘Crap()’

//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("[main] call Crap crap(42)\n");
    {
        Crap crap(42);
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }

    printf("[main] call Crap crap = Crap(14)\n");
    {
        Crap crap = Crap(14);
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }
    
    printf("[main] call Crap crap = dummy\n");
    {
        Crap crap = dummy;
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }

    printf("[main] call Crap crap(dummy)\n");
    {
        Crap crap(dummy);
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }

    printf("[main] call Crap crap(Crap(123))\n");
    {
        Crap crap(Crap(123));
        crap.getValue(value);
        crap.setValue(77);
        crap.getValue(value);
    }
    
    return 0;
}
