#ifndef MEMORYFLIPPERALGORITHM_HPP
#define MEMORYFLIPPERALGORITHM_HPP


#include <string>
#include <list>
#include <stdint.h>
#include "PrintMacros.hpp"


/**
 * @brief abstract class for memory flipper algorithms
 */
template<typename T>
class MemoryFlipperAlgorithm
{
public:
    MemoryFlipperAlgorithm(const std::string& algorithmIdentifierString, void (*fp)(T* const, T* const));
    void flipMemory(const uint32_t memoryBufferSizeInElements);
    inline const std::string& getAlgorithmIdentifier() const {return m_algorithmIdentifierString;};
    static void printRegisteredAlgorithms(void);
    uint32_t getNumberOfRegisteredAlgorithms(void) const;
    T* const getMemoryBuffer(const uint32_t memoryBufferSizeInElements);
    void freeMemoryBuffer(const T* memoryBufferHandler);
    void initMemoryBuffer(T* const memoryBufferHandler, uint32_t memoryBufferSizeInElements);

private:
    MemoryFlipperAlgorithm(const MemoryFlipperAlgorithm&);
    MemoryFlipperAlgorithm& operator=(const MemoryFlipperAlgorithm&);
    inline static void registerAlgorithm(const std::string& algorithmIdentifierString);

private:
    const std::string m_algorithmIdentifierString;
    //i want them const!
    static std::list<std::string> s_algorithmIdentifierList;
    void (*m_functionPointer2FlipFunctionImplementation)(T* const, T* const);
};


#include "MemoryFlipperAlgorithm.tpp"


#endif


