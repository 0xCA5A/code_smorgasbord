#include <iostream>
#include <thread>
#include <future>

#include "calculator.hpp"


#define NR_OF_CALCULATOR_GROUPS   5
#define CALCULATOR_GROUP_SIZE     4
#define NR_OF_CALCULATORS         (NR_OF_CALCULATOR_GROUPS * CALCULATOR_GROUP_SIZE)

int main()
{
    const unsigned int n = std::thread::hardware_concurrency();
    std::cout << n << " concurrent threads are supported.\n";

    std::cout << "[main] create calculator objects" << std::endl;
    Calculator* myCalculatorList[NR_OF_CALCULATORS];
    for (int i = 0; i < NR_OF_CALCULATORS; i++)
    {
        myCalculatorList[i] = new Calculator(i);
    }

    std::cout << "[main] get results from calculator objects and sum up" << std::endl;
    uint32_t sum;
    for (int i = 0; i < NR_OF_CALCULATORS; i++)
    {
        sum += myCalculatorList[i]->getResult();
        delete myCalculatorList[i];
    }

    std::cout << "[main] sum: " << sum << std::endl;

    std::cout << "[main] exit" << std::endl;
}