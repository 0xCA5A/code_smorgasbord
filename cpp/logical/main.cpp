#include <iostream>


bool checkForOne(int value)
{
    std::cout << "[i] function: " << __func__ << ", check value " << value << std::endl;
    if (value == 1)
    {
        std::cout << "\t* true" << std::endl;
        return true;
    }
    std::cout << "\t* false" << std::endl;
    return false;
}

bool checkForZero(int value)
{
    std::cout << "[i] function: " << __func__ << ", check value " << value << std::endl;
    if (value == 0)
    {
        std::cout << "\t* true" << std::endl;
        return true;
    }
    std::cout << "\t* false" << std::endl;
    return false;
}

void checkValuesPlusPlus(int checkForOneValue, int checkForZeroValue)
{
    std::cout << "[i] function: " << __func__ << std::endl;
    std::cout << "[i] checkForOne(" << checkForOneValue << ") && checkForZero(" << checkForZeroValue << ")" << std::endl;
    if (checkForOne(checkForOneValue) && checkForZero(checkForZeroValue))
    {
        std::cout << "[i] yes, good" << std::endl;
    }
    std::cout << std::endl << std::endl;
}

void checkValuesPlus(int checkForOneValue, int checkForZeroValue)
{
    std::cout << "[i] function: " << __func__ << std::endl;
    std::cout << "[i] checkForOne(" << checkForOneValue << ") & checkForZero(" << checkForZeroValue << ")" << std::endl;
    if (checkForOne(checkForOneValue) & checkForZero(checkForZeroValue))
    {
        std::cout << "[i] yes, good" << std::endl;
    }
    std::cout << std::endl << std::endl;
}

void checkValuesOrOr(int checkForOneValue, int checkForZeroValue)
{
    std::cout << "[i] function: " << __func__ << std::endl;
    std::cout << "[i] checkForOne(" << checkForOneValue << ") || checkForZero(" << checkForZeroValue << ")" << std::endl;
    if (checkForOne(checkForOneValue) || checkForZero(checkForZeroValue))
    {
        std::cout << "[i] yes, good" << std::endl;
    }
    std::cout << std::endl << std::endl;
}

void checkValuesOr(int checkForOneValue, int checkForZeroValue)
{
    std::cout << "[i] function: " << __func__ << std::endl;
    std::cout << "[i] checkForOne(" << checkForOneValue << ") | checkForZero(" << checkForZeroValue << ")" << std::endl;
    if (checkForOne(checkForOneValue) | checkForZero(checkForZeroValue))
    {
        std::cout << "[i] yes, good" << std::endl;
    }
    std::cout << std::endl << std::endl;
}

int main()
{
    std::cout << "[i] hello from " << __func__ << std::endl;

    //always both are evaluated
    checkValuesPlus(0, 0);
    checkValuesPlus(0, 1);
    checkValuesPlus(1, 0);
    checkValuesPlus(1, 1);

    //_not_ both are evaluated if first is false
    checkValuesPlusPlus(0, 0);
    checkValuesPlusPlus(0, 1);
    checkValuesPlusPlus(1, 0);
    checkValuesPlusPlus(1, 1);

    //always both are evaluated
    checkValuesOr(0, 0);
    checkValuesOr(0, 1);
    checkValuesOr(1, 0);
    checkValuesOr(1, 1);

    //_not_ both are evaluated if first is false
    checkValuesOrOr(0, 0);
    checkValuesOrOr(0, 1);
    checkValuesOrOr(1, 0);
    checkValuesOrOr(1, 1);

    return 0;
}

