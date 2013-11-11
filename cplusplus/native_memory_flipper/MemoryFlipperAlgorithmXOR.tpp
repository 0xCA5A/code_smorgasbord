#include "MemoryFlipperAlgorithmXOR.hpp"


template<typename T>
inline void MemoryFlipperAlgorithmXOR<T>::xorMemoryFlip(T* const dataElement0, T* const dataElement1)
{
        *dataElement0 ^= *dataElement1;
        *dataElement1 ^= *dataElement0;
        *dataElement0 ^= *dataElement1;
};


