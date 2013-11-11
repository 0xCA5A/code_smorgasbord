#ifndef MEMORYFLIPPERALGORITHMXOR_HPP
#define MEMORYFLIPPERALGORITHMXOR_HPP


#include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class MemoryFlipperAlgorithmXOR : public MemoryFlipperAlgorithm<T>
{
public:
    MemoryFlipperAlgorithmXOR(std::string& algorithmIdentifierString)
        : MemoryFlipperAlgorithm<T>(algorithmIdentifierString, xorMemorzFlip)
    {

//         pointer2FlipFunction
//         m_functionPointer2FlipFunctionImplementation = &this->xorMemorzFlip;
    };
    
    
//     virtual void flipMemory(T* const memoryBuffer, const uint32_t memoryBufferSizeInByte);
//         : MemoryFlipperAlgorithm(algorithmIdentifierString) {}

// private:
//     static const MixerAlgorithmDataElement s_mixerAlgorithmDataElement;
//     static const uint32_t s_nrOfSamplesPerChunk = 1;

    static void xorMemorzFlip(T*, T*) {

        std::cout << "hello from function" << std::endl;
        
    };
    

};


#include "MemoryFlipperAlgorithmXOR.tpp"


#endif