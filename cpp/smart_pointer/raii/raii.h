#ifndef RAII_H
#define RAII_H

#include <iostream>


class ResourceGuard
{
    public:
        ResourceGuard(int i);
        ~ResourceGuard();

    private:
        int* myIntArray;
};





#endif


