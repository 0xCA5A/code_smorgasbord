#ifndef MEMORYFLIPPERALGORITHMSHIFT_HPP
#define MEMORYFLIPPERALGORITHMSHIFT_HPP


#include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class MemoryFlipperAlgorithmShift : public MemoryFlipperAlgorithm<T>
{
public:
    MemoryFlipperAlgorithmShift(std::string& algorithmIdentifierString)
        : MemoryFlipperAlgorithm<T>(algorithmIdentifierString, &shiftMemoryFlip)
    {
        if ((sizeof(T) * 2) > sizeof(uint64_t)) {
            PRINT_FORMATTED_ERROR("bad configuration, shift result is wrong with (sizeof(T) * 2) > sizeof(uint64_t)");
        }
    };
    inline static void shiftMemoryFlip(T* const dataElement0, T* const dataElement1);
};


#include "MemoryFlipperAlgorithmShift.tpp"


#endif