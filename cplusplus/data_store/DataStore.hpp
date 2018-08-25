#ifndef DATASTORE_HPP
#define DATASTORE_HPP


#include <string>
#include <list>
#include <stdint.h>
#include "PrintMacros.hpp"


/**
 * @brief abstract class for data stores
 */
template < typename T > class DataStore {
private:
  DataStore & operator=(const DataStore &);
public:
DataStore(std::string i):identifier(i) {
  };
  virtual ~ DataStore() {
  };
  virtual void testInsert(const T) = 0;
  virtual bool testFind(const T) = 0;
  virtual T testAvg(void) = 0;
  virtual uint32_t testSize(void) = 0;
  inline std::string testIdentifier(void) {
    return identifier;
  };
protected:
  std::string identifier;
};

#endif
