#ifndef DATASTORESTLVECTOR_HPP
#define DATASTORESTLVECTOR_HPP


#include "DataStore.hpp"


template < typename T > class DataStoreStlVector:public DataStore < T > {
public:
DataStoreStlVector(void):DataStore < T > (typeid(*this).name()) {
  };
  void testInsert(const T);
  bool testFind(const T);
  T testAvg();
  uint32_t testSize(void);

  std::vector < T > data;

};

#include "DataStoreStlVector.tpp"

#endif
