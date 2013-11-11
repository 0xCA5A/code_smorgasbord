#include "MemoryFlipperAlgorithmCopy.hpp"


template<typename T>
inline void MemoryFlipperAlgorithmCopy<T>::copyMemoryFlip(T* const dataElement0, T* const dataElement1)
{
    T tempElement = *dataElement0;
    *dataElement0 = *dataElement1;
    *dataElement1 = tempElement;
};


