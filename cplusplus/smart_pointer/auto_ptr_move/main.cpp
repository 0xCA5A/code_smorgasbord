
#include <iostream>
#include <memory>
#include <string>


void printAutoPtr(const std::string& name, const std::auto_ptr<int>& autoPtr)
{
    std::cout << name << ": " << autoPtr.get() << std::endl;
    if (autoPtr.get())
    {
        std::cout << " value: " << *autoPtr << std::endl;
    }
}


//do here by reference to fix this...
void catchResource(std::auto_ptr<int> localInt)
{
    printAutoPtr("localInt.get():", localInt);
}


int main()
{
    std::cout << "std::auto_ptr<int> autoPtr1{new int(2011)}" << std::endl;
    std::auto_ptr<int> autoPtr1{new int(2011)};
    printAutoPtr("autoPtr1.get()", autoPtr1);

    std::cout << std::endl;

    std::cout << "std::auto_ptr<int> autoPtr2{autoPtr1}" << std::endl;
    std::auto_ptr<int> autoPtr2{autoPtr1};
    printAutoPtr("autoPtr1.get()", autoPtr1);
    printAutoPtr("autoPtr2.get()", autoPtr2);

    std::cout << std::endl;

    std::cout << "std::catchResource(autoPtr2)" << std::endl;
    catchResource(autoPtr2);
    printAutoPtr("autoPtr2.get()", autoPtr2);

    std::cout << std::endl;

}


