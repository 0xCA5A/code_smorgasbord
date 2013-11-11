#ifndef MEMORYFLIPPERALGORITHMXOR_HPP
#define MEMORYFLIPPERALGORITHMXOR_HPP


#include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class MemoryFlipperAlgorithmXOR : public MemoryFlipperAlgorithm<T>
{
public:
    MemoryFlipperAlgorithmXOR(std::string& algorithmIdentifierString)
        : MemoryFlipperAlgorithm<T>(algorithmIdentifierString, &xorMemoryFlip)
    {};
    inline static void xorMemoryFlip(T* const dataElement0, T* const dataElement1);
};


#include "MemoryFlipperAlgorithmXOR.tpp"


#endif