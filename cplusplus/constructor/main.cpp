#include <stdio.h>
#include "Crap.h"


int main()
{

    printf("[%s:%s:%d] create Crap globalDummyCrap(21) variable\n", __FILE__, __func__, __LINE__);
    Crap globalDummyCrap(21);

    printf("\n[%s:%s:%d] start\n\n", __FILE__, __func__, __LINE__);

    printf("\n[%s:%s:%d] call default constructor, Crap crap\n", __FILE__, __func__, __LINE__);
    {
        Crap crap;

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] call 'default' constructor, Crap crap()\n", __FILE__, __func__, __LINE__);
    {
        Crap crap();
        printf("\t[!] does not work, compiler complains non-class type Crap()!\n");
        //main.cpp:22:14: error: request for member ‘getValue’ in ‘crap’, which is of non-class type ‘Crap()’

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] call specified constructor, Crap crap(42)\n", __FILE__, __func__, __LINE__);
    {
        Crap crap(42);

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] call specified constructor with assignment, Crap crap = Crap(14)\n", __FILE__, __func__, __LINE__);
    {
        Crap crap = Crap(14);

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] call copy constructor with new object, Crap crap(Crap(123))\n", __FILE__, __func__, __LINE__);
    {
        Crap crap(Crap(123));

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] call copy constructor with existing object, Crap crap(globalDummyCrap)\n", __FILE__, __func__, __LINE__);
    {
        Crap crap(globalDummyCrap);

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] simple assignment, Crap crap = globalDummyCrap\n", __FILE__, __func__, __LINE__);
    {
        Crap crap = globalDummyCrap;

//         int value;
//         crap.getValue(value);
//         crap.setValue(77);
//         crap.getValue(value);
    }

    printf("\n[%s:%s:%d] assignment, globalDummyCrap = hugeCrap\n", __FILE__, __func__, __LINE__);
    {
        Crap smallCrap(123);
        Crap hugeCrap = smallCrap;
        globalDummyCrap = hugeCrap;

//         int value;
//         hugeCrap.getValue(value);
//         hugeCrap.setValue(77);
//         hugeCrap.getValue(value);
    }

    printf("\n[%s:%s:%d] assignment, smallCrap = globalDummyCrap\n", __FILE__, __func__, __LINE__);
    {
        Crap smallCrap(123);
        //huge crap is not used at all...
        Crap hugeCrap = smallCrap;
        smallCrap = globalDummyCrap;

//         int value;
//         smallCrap.getValue(value);
//         smallCrap.setValue(77);
//         smallCrap.getValue(value);
    }

    printf("\n[%s:%s:%d] new with pointer, Crap* p_pointedCrap = new Crap()\n", __FILE__, __func__, __LINE__);
    {
        Crap* p_pointedCrap = new Crap(123);
        delete p_pointedCrap;
    }

    printf("\n[%s:%s:%d] done\n\n", __FILE__, __func__, __LINE__);

    return 0;
}
