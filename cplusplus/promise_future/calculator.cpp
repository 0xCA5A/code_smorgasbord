#include <iostream>

#include "calculator.hpp"


Calculator::Calculator(uint32_t index) : m_index(index), m_promise()
{
    std::cout << "[i] " << __func__ << " constructor, index: " << index << std::endl;
    m_future = m_promise.get_future();

    std::thread m_workerThread(Calculator::calculateResult, move(m_promise));
    m_workerThread.join();
}

Calculator::~Calculator()
{
    std::cout << "[i] " << __func__ << " destructor, index: " << m_index << " , bye!" << std::endl;
}

void Calculator::calculateResult(std::promise<uint32_t> intPromise)
{
    //heavy calculation...
    uint32_t sum = 0;
    for (int i = 0; i < 444444444; i++)
    {
        sum += i;
    }
    intPromise.set_value(sum);
}

uint32_t Calculator::getResult(void)
{
    std::cout << "[i] in function " << __func__ << " index: " << m_index << std::endl;
    const uint32_t result = m_promise.get_future().get();

    std::cout << "[i] leave function " << __func__ << " index: " << m_index << std::endl;

    return result;
}
