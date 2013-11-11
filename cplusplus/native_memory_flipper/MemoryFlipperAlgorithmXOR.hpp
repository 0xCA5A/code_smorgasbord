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

    inline static void xorMemoryFlip(T* dataElement0, T* dataElement1) {
        *dataElement0 ^= *dataElement1;
        *dataElement1 ^= *dataElement0;
        *dataElement0 ^= *dataElement1;
    };
};


#include "MemoryFlipperAlgorithmXOR.tpp"


#endif