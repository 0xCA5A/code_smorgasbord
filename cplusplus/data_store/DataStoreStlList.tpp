#include "DataStoreStlList.hpp"
#include <string>

template < typename T > void DataStoreStlList <
  T >::testInsert(const T value)
{
  data.push_back(value);
};

template < typename T > bool DataStoreStlList <
  T >::testFind(const T findValue)
{
for (auto const &value:data) {
    if (value == findValue)
      return true;
  }
  return false;
};

template < typename T > T DataStoreStlList < T >::testAvg(void)
{
  double sum = 0;
for (auto const &value:data) {
    sum += value;
  }
  return (T) sum / data.size();
};

template < typename T > uint32_t DataStoreStlList < T >::testSize(void)
{
  return data.size();
};
