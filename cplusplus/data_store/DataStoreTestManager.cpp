#include "DataStoreTestManager.hpp"

#include "DataStore.hpp"
#include "DataStoreStlVector.hpp"
#include "DataStoreStlMap.hpp"
#include "DataStoreStlUnorderedMap.hpp"
#include "DataStoreStlList.hpp"

#include "PrintMacros.hpp"
#include "UsTimer.hpp"

#include <stdlib.h>
#include <time.h>
#include <vector>
#include <cstdint>
#include <algorithm>            // std::find
#include <cstdint>
#include <assert.h>
#include <chrono>
#include <sstream>


uint32_t DataStoreTestManager::getAvgValue(const std::vector < uint32_t >
                                           &records)
{
  return (uint32_t) std::accumulate(records.begin(), records.end(),
                                    0LL) / records.size();
}


uint32_t DataStoreTestManager::getNonExistingValue(const std::vector <
                                                   uint32_t > &records)
{
  uint32_t value = 42;
  while (std::find(records.begin(), records.end(), value) != records.end()) {
    value++;
  }
  return value;
}


uint32_t DataStoreTestManager::getExistingValue(const std::vector <
                                                uint32_t > &records)
{
  return records.at(records.size() / 2);
}


void DataStoreTestManager::generateRandomRecords(uint32_t n,
                                                 std::vector < uint32_t >
                                                 &records)
{
  LOG_INFO("Generate " + std::to_string(n) +
           " random data test records (might take a while...)");
  srand(time(NULL));
  uint32_t value;
  for (uint32_t cnt = 0; cnt < n; cnt++) {
    do {
      // rand() returns from 0 to RAND_MAX (2147483647), so 31bit.
      value = rand();
    }
    while (std::find(records.begin(), records.end(), value) !=
           records.end());

    LOG_DEBUG("Add unique value " + std::to_string(value) + " to records");
    records.push_back(value);
  }
  assert(records.size() == n);
}

void DataStoreTestManager::runTest(DataStore < uint32_t > *ds,
                                   std::vector < uint32_t > &testRecords)
{
  LOG_INFO("Evaluate data store object '" + ds->testIdentifier() +
           "' performance");

  // Fill data store with test data
  {
    UsTimer t("Fill data store");
    for (auto it = testRecords.begin(); it != testRecords.end(); it++) {
      ds->testInsert(*it);
    }
    assert(ds->testSize() == testRecords.size());
  }

  // Access data store
  // find existing
  {
    UsTimer t("Access data store (existing)");
    bool status = ds->testFind(getExistingValue(testRecords));
    assert(status == true);
  }

  // Access data store
  // find non-existing
  {
    UsTimer t("Access data store (non-existing)");
    bool status = ds->testFind(getNonExistingValue(testRecords));
    assert(status == false);
  }
  // Access data store
  // get avg data
  {
    UsTimer t("Access data store (avg)");
    double value = ds->testAvg();
    assert(value == getAvgValue(testRecords));
  }

  LOG_INFO("Done with '" + ds->testIdentifier() + "'\n");
}

void DataStoreTestManager::run(void)
{
  // generate n unique, unsorted random samples
  std::vector < uint32_t > testRecords;
  uint32_t numRecords = 100000;
  {
    UsTimer t("Generate random records");
    generateRandomRecords(numRecords, testRecords);
  }
  LOG_INFO("Run tests with " + std::to_string(numRecords) +
           " unique data records (record width: " +
           std::to_string(sizeof(uint32_t)) + " bytes)");

  ds = new DataStoreStlVector < uint32_t > ();
  runTest(ds, testRecords);
  delete ds;

  ds = new DataStoreStlList < uint32_t > ();
  runTest(ds, testRecords);
  delete ds;

  ds = new DataStoreStlMap < uint32_t > ();
  runTest(ds, testRecords);
  delete ds;

  ds = new DataStoreStlUnorderedMap < uint32_t > ();
  runTest(ds, testRecords);
  delete ds;
};
