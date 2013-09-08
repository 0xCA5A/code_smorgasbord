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
        static void calculateResult(std::promise<uint32_t> intPromise);

    private:
        uint32_t m_index;
        std::future<uint32_t> m_future;
        std::promise<uint32_t> m_promise;
        std::thread m_workerThread;
};


#endif
