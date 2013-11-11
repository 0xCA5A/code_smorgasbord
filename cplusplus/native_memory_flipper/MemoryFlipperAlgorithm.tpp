#include "MemoryFlipperAlgorithm.hpp"
#include <iostream>
#include <stdlib.h>


#include "PrintMacros.hpp"



template<typename T>
std::list<std::string> MemoryFlipperAlgorithm<T>::s_algorithmIdentifierList;



template<typename T>
MemoryFlipperAlgorithm<T>::MemoryFlipperAlgorithm(const std::string& algorithmIdentifierString, void (*fp)(T*, T*))
    : m_algorithmIdentifierString(algorithmIdentifierString)
    , m_functionPointer2FlipFunctionImplementation(fp)
{
    registerAlgorithm(m_algorithmIdentifierString);
}


template<typename T>
inline void MemoryFlipperAlgorithm<T>::registerAlgorithm(const std::string& algorithmIdentifierString)
{
    MemoryFlipperAlgorithm::s_algorithmIdentifierList.push_back(algorithmIdentifierString);
}


template<typename T>
void MemoryFlipperAlgorithm<T>::printRegisteredAlgorithms(void) const
{
    std::list<std::string>::iterator iterator;
    for (iterator = MemoryFlipperAlgorithm::s_algorithmIdentifierList.begin(); iterator != MemoryFlipperAlgorithm::s_algorithmIdentifierList.end(); ++iterator) {
        std::cout << " * " << *iterator;
    }
}


template<typename T>
uint32_t MemoryFlipperAlgorithm<T>::getNumberOfRegisteredAlgorithms(void) const
{
    return MemoryFlipperAlgorithm::s_algorithmIdentifierList.size();
}


template<typename T>
void MemoryFlipperAlgorithm<T>::flipMemory(T* const memoryBuffer, const uint32_t memoryBufferSizeInByte)
{

    //check if function pointer was set properly...
    if (m_functionPointer2FlipFunctionImplementation == NULL) {

        PRINT_FORMATTED_ERROR("m_pointer2FlipFunction was NULL, bad algorithm implementation...");
        exit(EXIT_FAILURE);
        
    }
        
    
    const uint32_t numberOfFlips = memoryBufferSizeInByte / sizeof(T);
    
    for (uint32_t flipCount = 0; flipCount < numberOfFlips; ++flipCount) {
        
        //         pointer2FlipFunction();
//         (*fp)(12, memoryBuffer, memoryBuffer)
//         
//         
        
        (*m_functionPointer2FlipFunctionImplementation)(memoryBuffer, memoryBuffer);

        
    }
    
    
}
