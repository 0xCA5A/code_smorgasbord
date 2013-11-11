#ifndef MEMORYFLIPPERALGORITHMXOR_HPP
#define MEMORYFLIPPERALGORITHMXOR_HPP


#include "MemoryFlipperAlgorithm.hpp"


template<typename T, const uint32_t memoryBlockSizeInByte>
class MemoryFlipperAlgorithmXOR : public MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>
{
public:
    MemoryFlipperAlgorithmXOR(std::string& algorithmIdentifierString) :  MemoryFlipperAlgorithm<T, memoryBlockSizeInByte>(algorithmIdentifierString)
    {
            
    };
    
    
    virtual void flipMemory(T* const memoryBuffer, const uint32_t bufferSize);
//         : MemoryFlipperAlgorithm(algorithmIdentifierString) {}

private:
//     static const MixerAlgorithmDataElement s_mixerAlgorithmDataElement;
//     static const uint32_t s_nrOfSamplesPerChunk = 1;
};


#include "MemoryFlipperAlgorithmXOR.tpp"


#endif