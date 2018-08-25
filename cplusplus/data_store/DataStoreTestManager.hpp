#ifndef DATASTORETEST_HPP
#define DATASTORETEST_HPP

#include <cstdint>
#include <vector>
#include <functional>
#include <chrono>

#include "DataStore.hpp"



class DataStoreTestManager {
public:
  DataStoreTestManager(void) {
  };
  void run(void);
  void generateRandomRecords(uint32_t n,
                             std::vector < uint32_t > &records);
  uint32_t getNonExistingValue(const std::vector < uint32_t > &records);
  uint32_t getExistingValue(const std::vector < uint32_t > &records);
  uint32_t getAvgValue(const std::vector < uint32_t > &records);
  void runTest(DataStore < uint32_t > *ds,
               std::vector < uint32_t > &records);
private:
  DataStore < uint32_t > *ds;
};

#endif
