#include "MemoryFlipperAlgorithmShift.hpp"


template<typename T>
inline void MemoryFlipperAlgorithmShift<T>::shiftMemoryFlip(T* const dataElement0, T* const dataElement1)
{
    //NOTE: this is bad code, not portable!!!
    //NOTE: does not work with values > 32bit
    const uint64_t shiftBuffer = ((uint64_t)(*dataElement0) << (sizeof(T) * 8)) | ((uint64_t)(*dataElement1));
    *dataElement0 = *(T*)(&shiftBuffer);
    *dataElement1 = *((T*)(&shiftBuffer) + 1);
};


