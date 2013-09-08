#include <iostream>

#include "calculator.hpp"


Calculator::Calculator(uint32_t index) : m_index(index)
{
    std::cout << "[E] " << __func__ << " constructor, index: " << index << std::endl;
    m_future = std::async(Calculator::calculateResult, index);
    std::cout << "[L] " << __func__ << " constructor, index: " << index << std::endl;
}

Calculator::~Calculator()
{
    std::cout << "[E] " << __func__ << " destructor, index: " << m_index << " , bye!" << std::endl;
}

uint32_t Calculator::calculateResult(uint32_t index)
{
    //heavy calculation...
    uint32_t sum = 0;
    for (uint32_t i = 0; i < (index * 44444444); i++)
    {
        sum += i;
    }
    return sum;
}

uint32_t Calculator::getResult(void)
{
    std::cout << "[E] function " << __func__ << " index: " << m_index << std::endl;
    const uint32_t result = m_future.get();

    std::cout << "[L] function " << __func__ << " index: " << m_index << ", result is " << result << std::endl;

    return result;
}
