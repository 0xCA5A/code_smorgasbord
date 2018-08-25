#include "DataStoreStlMap.hpp"
#include <functional>
#include <string>

#include <functional>
#include <string>
#include <unordered_set>

template < typename T > void DataStoreStlMap <
  T >::testInsert(const T value)
{
  const T key = std::hash < T > ()(value);
  data.insert(std::pair < uint32_t, T > (key, value));
};

template < typename T > bool DataStoreStlMap <
  T >::testFind(const T findValue)
{
  const T key = std::hash < T > ()(findValue);
  auto it = data.find(key);
  const bool foundStatus = (it != data.end());
  return foundStatus;
};

template < typename T > T DataStoreStlMap < T >::testAvg(void)
{
  double sum = 0;
for (auto const &value:data) {
    sum += value.second;
  }
  return (T) sum / data.size();
};

template < typename T > uint32_t DataStoreStlMap < T >::testSize(void)
{
  return data.size();
};
