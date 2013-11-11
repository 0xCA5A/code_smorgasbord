#include <iostream>
#include <cstdlib>

#include "MemoryFlipperApplication.hpp"
#include "MemoryFlipperAlgorithmXOR.hpp"


int main(int argc, char* argv[])
{
//     MemoryFlipperApplication memoryFlipperApplication;

    std::string algorithmName;

    const uint32_t memoryBufferSizeInByte = 1024 * 1024;
    void* memoryBuffer = malloc(memoryBufferSizeInByte);

    //get the algorithms
    algorithmName = "memoryFlipperAlgorithmXOR";
    MemoryFlipperAlgorithmXOR<uint8_t> memoryFlipperAlgorithmXOR(algorithmName);
    memoryFlipperAlgorithmXOR.flipMemory((uint8_t*) memoryBuffer, memoryBufferSizeInByte);

//     MemoryFlipperAlgorithmXOR<uint32_t, 123> memoryFlipperAlgorithmXOR(algorithmName);
//     memoryFlipperAlgorithmXOR.flipMemory((uint8_t*) memoryBuffer, memoryBufferSizeInByte);

    
//     algorithmName = "mixerAlgorithmSimpleAddWithNormalization";
//     MixerAlgorithmSimpleAddWithNormalization mixerAlgorithmSimpleAddWithNormalization(algorithmName);
// 
//     algorithmName = "mixerAlgorithmFancyAddWithNormalization";
//     MixerAlgorithmFancyAddWithNormalization mixerAlgorithmFancyAddWithNormalization(algorithmName);
// 
//     algorithmName = "mixerAlgorithmRMSGainBeforeSum";
//     MixerAlgorithmRMSGainBeforeSum mixerAlgorithmRMSGainBeforeSum(algorithmName, mixerApplication.getNrOfInputFiles());
// 
//     algorithmName = "mixerAlgorithmRMSGainBeforeAndAfterSum";
//     MixerAlgorithmRMSGainBeforeAndAfterSum mixerAlgorithmRMSGainBeforeAndAfterSum(algorithmName, mixerApplication.getNrOfInputFiles());
// 
//     algorithmName = "mixerAlgorithmRMSGainAfterSum";
//     MixerAlgorithmRMSGainAfterSum mixerAlgorithmRMSGainAfterSum(algorithmName);


    //start mixing
//     mixerApplication.setStrategy(&mixerAlgorithmSimpleAddWithClipping);
//     const std::string mixerAlgorithmSimpleAddWithClippingMixOutputFileName(mixerAlgorithmSimpleAddWithClipping.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmSimpleAddWithClippingMixOutputFileName);
// 
//     mixerApplication.setStrategy(&mixerAlgorithmSimpleAddWithNormalization);
//     const std::string mixerAlgorithmSimpleAddWithNormalizationMixFileName(mixerAlgorithmSimpleAddWithNormalization.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmSimpleAddWithNormalizationMixFileName);
// 
//     mixerApplication.setStrategy(&mixerAlgorithmFancyAddWithNormalization);
//     const std::string mixerAlgorithmFancyAddWithNormalizationMixFileName(mixerAlgorithmFancyAddWithNormalization.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmFancyAddWithNormalizationMixFileName);
// 
//     mixerApplication.setStrategy(&mixerAlgorithmRMSGainBeforeSum);
//     const std::string mixerAlgorithmRMSGainBeforeSumMixFileName(mixerAlgorithmRMSGainBeforeSum.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmRMSGainBeforeSumMixFileName);
// 
//     mixerApplication.setStrategy(&mixerAlgorithmRMSGainBeforeAndAfterSum);
//     const std::string mixerAlgorithmRMSGainBeforeAndAfterSumMixFileName(mixerAlgorithmRMSGainBeforeAndAfterSum.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmRMSGainBeforeAndAfterSumMixFileName);
// 
//     mixerApplication.setStrategy(&mixerAlgorithmRMSGainAfterSum);
//     const std::string mixerAlgorithmRMSGainAfterSumMixFileName(mixerAlgorithmRMSGainAfterSum.getAlgorithmIdentifier() + "MixResultData.wav");
//     mixerApplication.mixRIFFWAVEFiles(mixerAlgorithmRMSGainAfterSumMixFileName);

    return 0;
}