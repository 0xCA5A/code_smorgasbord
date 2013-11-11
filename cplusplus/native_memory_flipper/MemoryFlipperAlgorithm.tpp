#include <iostream>
#include <stdlib.h>
#include <ctime>
#include <cstring>

#include "MemoryFlipperAlgorithm.hpp"
#include "PrintMacros.hpp"


template<typename T>
std::list<std::string> MemoryFlipperAlgorithm<T>::s_algorithmIdentifierList;


template<typename T>
MemoryFlipperAlgorithm<T>::MemoryFlipperAlgorithm(const std::string& algorithmIdentifierString, void (*fp)(T* const,T* const))
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
void MemoryFlipperAlgorithm<T>::printRegisteredAlgorithms(void)
{
    PRINT_FORMATTED_INFO("registered algorithms:");
    std::list<std::string>::iterator iterator;
    for (iterator = MemoryFlipperAlgorithm::s_algorithmIdentifierList.begin(); iterator != MemoryFlipperAlgorithm::s_algorithmIdentifierList.end(); ++iterator) {
        PRINT_FORMATTED_LIST_ELEMENT(*iterator);
    }
}


template<typename T>
uint32_t MemoryFlipperAlgorithm<T>::getNumberOfRegisteredAlgorithms(void) const
{
    return MemoryFlipperAlgorithm::s_algorithmIdentifierList.size();
}


template<typename T>
T* const MemoryFlipperAlgorithm<T>::getMemoryBuffer(const uint32_t memoryBufferSizeInElements)
{
    PRINT_FORMATTED_INFO("get memory buffer, " << memoryBufferSizeInElements << " elements, " << memoryBufferSizeInElements * sizeof(T) << " byte...");
    return new T[memoryBufferSizeInElements];
}


template<typename T>
void MemoryFlipperAlgorithm<T>::freeMemoryBuffer(const T* memoryBufferHandler)
{
    PRINT_FORMATTED_INFO("free memory buffer");
    delete memoryBufferHandler;
}


template<typename T>
void MemoryFlipperAlgorithm<T>::initMemoryBuffer(T* const memoryBufferHandler, uint32_t memoryBufferSizeInElements)
{
    PRINT_FORMATTED_INFO("init memory buffer");

//     for (uint32_t i = 0; i < memoryBufferSizeInElements; ++i) {
//         memoryBufferHandler[i] = i;
//     }

    memset(memoryBufferHandler, 0x42, memoryBufferSizeInElements * sizeof(T));
}


template<typename T>
void MemoryFlipperAlgorithm<T>::flipMemory(const uint32_t memoryBufferSizeInElements)
{
    //check if function pointer was set properly...
    if (m_functionPointer2FlipFunctionImplementation == NULL) {
        PRINT_FORMATTED_ERROR("m_pointer2FlipFunction was NULL, bad algorithm implementation...");
        exit(EXIT_FAILURE);
    }

    //get memory
    T* const memoryBuffer = getMemoryBuffer(memoryBufferSizeInElements);
    initMemoryBuffer(memoryBuffer, memoryBufferSizeInElements);

    PRINT_FORMATTED_INFO(memoryBufferSizeInElements / 2 << " flips todo, go!");

    std::clock_t startTime = std::clock();
    for (uint32_t flipIndex = 0; flipIndex < memoryBufferSizeInElements; flipIndex += 2) {
        (*m_functionPointer2FlipFunctionImplementation)(memoryBuffer + flipIndex, (memoryBuffer + 1) + flipIndex);
    }
    double swapDuration = ( std::clock() - startTime);
    PRINT_FORMATTED_INFO("total swap duration in clocks: " << swapDuration);
    PRINT_FORMATTED_INFO("total swap duration in seconds: " << swapDuration / (double) CLOCKS_PER_SEC);

    //clean up
    freeMemoryBuffer(memoryBuffer);
}
