#ifndef _CALCULATOR_HPP_
#define _CALCULATOR_HPP_

#include <future>

class Calculator
{
    public:
        Calculator(uint32_t index);
        ~Calculator();
        uint32_t getResult(void);

    private:
        static uint32_t calculateResult(uint32_t index);

    private:
        uint32_t m_index;
        std::future<uint32_t> m_future;
};


#endif
