#ifndef DATASTORESTLMAP_HPP
#define DATASTORESTLMAP_HPP


#include "DataStore.hpp"
#include <map>


template < typename T > class DataStoreStlMap:public DataStore < T > {
public:
DataStoreStlMap(void):DataStore < T > (typeid(*this).name()) {
  };
  void testInsert(const T);
  bool testFind(const T);
  T testAvg();
  uint32_t testSize(void);

  std::map < uint32_t, T > data;
};

#include "DataStoreStlMap.tpp"

#endif
