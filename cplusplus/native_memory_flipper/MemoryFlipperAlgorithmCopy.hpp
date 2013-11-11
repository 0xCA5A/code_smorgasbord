#ifndef MEMORYFLIPPERALGORITHMCOPY_HPP
#define MEMORYFLIPPERALGORITHMCOPY_HPP


#include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class MemoryFlipperAlgorithmCopy : public MemoryFlipperAlgorithm<T>
{
public:
    MemoryFlipperAlgorithmCopy(std::string& algorithmIdentifierString)
        : MemoryFlipperAlgorithm<T>(algorithmIdentifierString, &copyMemoryFlip)
    {};
    inline static void copyMemoryFlip(T* const dataElement0, T* const dataElement1);
};


#include "MemoryFlipperAlgorithmCopy.tpp"


#endif