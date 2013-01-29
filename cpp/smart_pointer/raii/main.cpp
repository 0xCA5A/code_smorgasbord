
#include <iostream>
#include "raii.h"


int main()
{
    ResourceGuard myRescourceGuard(123);
    std::cout << "before scope" << std::endl;
    {
        ResourceGuard myRescourceGuardInScope(123);
    }
    std::cout << "after scope" << std::endl;
}


