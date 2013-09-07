
#include "raii.h"


    ResourceGuard::ResourceGuard(int i)
    {
        myIntArray = new int[i];
        std::cout << "constructor of myIntArray: " << myIntArray << std::endl;
    }

    ResourceGuard::~ResourceGuard()
    {
        delete [] myIntArray;
        std::cout << "destructor of myIntArray: " << myIntArray << std::endl;
    }