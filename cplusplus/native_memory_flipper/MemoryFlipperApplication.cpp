#include <sndfile.h>
#include <cstdlib>
#include <strings.h>
#include <limits>
#include <cstring>
#include <cstdio>
#include <ctime>

#include "MemoryFlipperApplication.hpp"
#include "MemoryFlipperAlgorithm.hpp"
#include "MemoryFlipperAlgorithmXOR.hpp"

#include "PrintMacros.hpp"


MemoryFlipperApplication::MemoryFlipperApplication(void)
{
    PRINT_FORMATTED_INFO("init internal data structures");

//     if (m_nrOfInputFiles < 2) {
//         PRINT_FORMATTED_ERROR("expect at least two input files...");
//         exit(EXIT_FAILURE);
//     }
// 
//     m_p_soundFileHandlerArray = new SNDFILE*[m_nrOfInputFiles];
//     m_p_soundFileInfoArray = new SF_INFO[m_nrOfInputFiles];
//     m_p_gainFactorArray = new float[m_nrOfInputFiles];
// 
//     for (unsigned int i = 0; i < m_nrOfInputFiles; i++) {
//         m_mixerInputRIFFWAVEFileNameList.push_back(inputFileNameArray[i]);
//         m_p_gainFactorArray[i] = 1.0;
//     }
// 
//     tryToOpenRIFFWAVEFiles();
//     checkWaveFileConfiguration();

    PRINT_FORMATTED_INFO("init successful");
}


MemoryFlipperApplication::~MemoryFlipperApplication(void)
{
    /*
    
    PRINT_FORMATTED_INFO("delete internal data structures and close input files");

    //close input files
    PRINT_FORMATTED_INFO("close wave files...");
    for (unsigned int index = 0; index < m_mixerInputRIFFWAVEFileNameList.size(); ++index) {
        sf_close(m_p_soundFileHandlerArray[index]);
    }*/
}
