#ifndef DATASTORESTLLIST_HPP
#define DATASTORESTLLIST_HPP


#include "DataStore.hpp"


template < typename T > class DataStoreStlList:public DataStore < T > {
public:
DataStoreStlList(void):DataStore < T > (typeid(*this).name()) {
  };
  void testInsert(const T);
  bool testFind(const T);
  T testAvg();
  uint32_t testSize(void);

  std::list < T > data;

};

#include "DataStoreStlList.tpp"

#endif
