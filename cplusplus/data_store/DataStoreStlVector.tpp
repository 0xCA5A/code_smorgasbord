#include "DataStoreStlVector.hpp"
#include <string>

template < typename T > void DataStoreStlVector <
  T >::testInsert(const T value)
{
  data.push_back(value);
};

template < typename T > bool DataStoreStlVector <
  T >::testFind(const T findValue)
{
for (auto const &value:data) {
    if (value == findValue)
      return true;
  }
  return false;
};

template < typename T > T DataStoreStlVector < T >::testAvg(void)
{
  double sum = 0;
for (auto const &value:data) {
    sum += value;
  }
  return (T) sum / data.size();
};

template < typename T > uint32_t DataStoreStlVector < T >::testSize(void)
{
  return data.size();
};
