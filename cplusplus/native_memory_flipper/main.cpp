#include <iostream>
#include <cstdlib>

#include "MemoryFlipperApplication.hpp"
#include "MemoryFlipperAlgorithmXOR.hpp"
#include "PrintMacros.hpp"



int main(int argc, char* argv[])
{
    std::string algorithmName;
    const uint32_t memoryBufferSizeInByte = 1024 * 1024 * 512;

    algorithmName = "memoryFlipperAlgorithmXOR";
    MemoryFlipperAlgorithmXOR<uint8_t> memoryFlipperAlgorithmXOR(algorithmName);
    memoryFlipperAlgorithmXOR.flipMemory(memoryBufferSizeInByte);

    return 0;
}