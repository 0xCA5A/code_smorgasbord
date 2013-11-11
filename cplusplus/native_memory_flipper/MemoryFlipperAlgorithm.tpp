#include "MemoryFlipperAlgorithm.hpp"
#include <iostream>



template<typename T, const uint32_t memoryBlockSizeInByte>
std::list<std::string> MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>::s_algorithmIdentifierList;



template<typename T, const uint32_t memoryBlockSizeInByte>
MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>::MemoryFlipperAlgorithm(const std::string& algorithmIdentifierString)
    : m_algorithmIdentifierString(algorithmIdentifierString)
{
    registerAlgorithm(m_algorithmIdentifierString);
}


template<typename T, const uint32_t memoryBlockSizeInByte>
inline void MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>::registerAlgorithm(const std::string& algorithmIdentifierString)
{
    MemoryFlipperAlgorithm::s_algorithmIdentifierList.push_back(algorithmIdentifierString);
}


template<typename T, const uint32_t memoryBlockSizeInByte>
void MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>::printRegisteredAlgorithms(void) const
{
    std::list<std::string>::iterator iterator;
    for (iterator = MemoryFlipperAlgorithm::s_algorithmIdentifierList.begin(); iterator != MemoryFlipperAlgorithm::s_algorithmIdentifierList.end(); ++iterator) {
        std::cout << " * " << *iterator;
    }
}


template<typename T, const uint32_t memoryBlockSizeInByte>
uint32_t MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>::getNumberOfRegisteredAlgorithms(void) const
{
    return MemoryFlipperAlgorithm::s_algorithmIdentifierList.size();
}
