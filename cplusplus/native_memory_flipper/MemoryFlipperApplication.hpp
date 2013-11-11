#ifndef MEMORYFLIPPERAPPLICATION_HPP
#define MEMORYFLIPPERAPPLICATION_HPP


#include <stdint.h>
#include <list>
#include "MemoryFlipperAlgorithmXOR.hpp"


// class MemoryFlipperAlgorithm;


class MemoryFlipperApplication
{
public:
    MemoryFlipperApplication(void);
    ~MemoryFlipperApplication(void);
//     void setStrategy(const MemoryFlipperAlgorithm& memoryFlipperAlgorithm);
//     void startMemoryFlipperAlgorithm(void);

private:
    MemoryFlipperApplication(const MemoryFlipperApplication&);
    MemoryFlipperApplication& operator=(const MemoryFlipperApplication&);
//     void checkWaveFileConfiguration(void) const;

private:
//     std::list<MemoryFlipperAlgorithm>  memoryFlipperAlgorithmList;

};

#endif