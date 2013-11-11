#include <iostream>
#include <cstdlib>

#include "PrintMacros.hpp"
#include "MemoryFlipperAlgorithmXOR.hpp"
#include "MemoryFlipperAlgorithmShift.hpp"
#include "MemoryFlipperAlgorithmCopy.hpp"


int main(int argc, char* argv[])
{
    std::string algorithmName;
    const uint32_t memoryBufferSizeInByte = 1024 * 1024 * 128;


    //NOTE: naming is bad, typeid does not work for standard types...


    ////////////////////////////////////////////////////////////////////////////////
    // uint8_t
    ////////////////////////////////////////////////////////////////////////////////
    {
    algorithmName = "memoryFlipperAlgorithmXOR_uint8_t";
    MemoryFlipperAlgorithmXOR<uint8_t> memoryFlipperAlgorithmXOR(algorithmName);
    algorithmName = "memoryFlipperAlgorithmShift_uint8_t";
    MemoryFlipperAlgorithmShift<uint8_t> memoryFlipperAlgorithmShift(algorithmName);
    algorithmName = "memoryFlipperAlgorithmCopy_uint8_t";
    MemoryFlipperAlgorithmCopy<uint8_t> memoryFlipperAlgorithmCopy(algorithmName);

    //NOTE: notice that this is a bad implementation, static stuff is generated for all template types...
    MemoryFlipperAlgorithm<uint8_t>::printRegisteredAlgorithms();

    memoryFlipperAlgorithmXOR.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmShift.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmCopy.flipMemory(memoryBufferSizeInByte);
    }
    ////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////
    // uint16_t
    ////////////////////////////////////////////////////////////////////////////////
    {
    algorithmName = "memoryFlipperAlgorithmXOR_uint16_t";
    MemoryFlipperAlgorithmXOR<uint16_t> memoryFlipperAlgorithmXOR(algorithmName);
    algorithmName = "memoryFlipperAlgorithmShift_uint16_t";
    MemoryFlipperAlgorithmShift<uint16_t> memoryFlipperAlgorithmShift(algorithmName);
    algorithmName = "memoryFlipperAlgorithmCopy_uint16_t";
    MemoryFlipperAlgorithmCopy<uint16_t> memoryFlipperAlgorithmCopy(algorithmName);

    //NOTE: notice that this is a bad implementation, static stuff is generated for all template types...
    MemoryFlipperAlgorithm<uint16_t>::printRegisteredAlgorithms();

    memoryFlipperAlgorithmXOR.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmShift.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmCopy.flipMemory(memoryBufferSizeInByte);
    }
    ////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////
    // uint32_t
    ////////////////////////////////////////////////////////////////////////////////
    {
    algorithmName = "memoryFlipperAlgorithmXOR_uint32_t";
    MemoryFlipperAlgorithmXOR<uint32_t> memoryFlipperAlgorithmXOR(algorithmName);
    algorithmName = "memoryFlipperAlgorithmShift_uint32_t";
    MemoryFlipperAlgorithmShift<uint32_t> memoryFlipperAlgorithmShift(algorithmName);
    algorithmName = "memoryFlipperAlgorithmCopy_uint32_t";
    MemoryFlipperAlgorithmCopy<uint32_t> memoryFlipperAlgorithmCopy(algorithmName);

    //NOTE: notice that this is a bad implementation, static stuff is generated for all template types...
    MemoryFlipperAlgorithm<uint32_t>::printRegisteredAlgorithms();

    memoryFlipperAlgorithmXOR.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmShift.flipMemory(memoryBufferSizeInByte);
    memoryFlipperAlgorithmCopy.flipMemory(memoryBufferSizeInByte);
    }
    ////////////////////////////////////////////////////////////////////////////////

    return 0;
}