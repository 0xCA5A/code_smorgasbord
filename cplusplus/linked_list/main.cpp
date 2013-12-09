#include <iostream>

#include <inttypes.h>

#include "LinkedList.hpp"



int main()
{
    LinkedList<uint64_t> myFirstList;
    LinkedList<uint64_t> mySecondList;

    std::cout << "[i] current number of elements in list: " << myFirstList.getNumberOfElements() << std::endl;

    uint64_t value = 0xDEADBEEF;
    for (unsigned int i = 0; i < 2000; i++) {
        myFirstList.addObjectHead(++value);
        myFirstList.addObjectHead(++value);
        myFirstList.popObjectHead();
    }

    std::cout << "[i] current number of elements in list: " << myFirstList.getNumberOfElements() << std::endl;

    for (uint32_t i = 0; i < 2000; i++) {
        myFirstList.popObjectHead();
    }

    std::cout << "[i] current number of elements in list: " << myFirstList.getNumberOfElements() << std::endl;


    return 0;
}
