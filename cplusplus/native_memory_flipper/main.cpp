#include <iostream>
#include <cstdlib>

#include "PrintMacros.hpp"
#include "MemoryFlipperAlgorithmShift.hpp"
#include "MemoryFlipperAlgorithmXOR.hpp"


int main(int argc, char* argv[])
{
    std::string algorithmName;
    const uint32_t memoryBufferSizeInByte = 1024 * 1024 * 512;

    algorithmName = "memoryFlipperAlgorithmXOR";
    MemoryFlipperAlgorithmXOR<uint8_t> memoryFlipperAlgorithmXOR(algorithmName);

    MemoryFlipperAlgorithm<uint32_t>::printRegisteredAlgorithms();
    MemoryFlipperAlgorithm<uint8_t>::printRegisteredAlgorithms();

    memoryFlipperAlgorithmXOR.flipMemory(memoryBufferSizeInByte);

    return 0;
}