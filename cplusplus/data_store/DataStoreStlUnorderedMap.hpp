#ifndef DATASTORESTLUNORDEREDMAP_HPP
#define DATASTORESTLUNORDEREDMAP_HPP


#include "DataStore.hpp"
#include <unordered_map>


template < typename T > class DataStoreStlUnorderedMap:public DataStore < T >
{
public:
DataStoreStlUnorderedMap(void):DataStore < T > (typeid(*this).name()) {
  };
  void testInsert(const T);
  bool testFind(const T);
  T testAvg();
  uint32_t testSize(void);

  std::unordered_map < uint32_t, T > data;
};

#include "DataStoreStlUnorderedMap.tpp"

#endif
