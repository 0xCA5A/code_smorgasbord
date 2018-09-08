cmake_minimum_required (VERSION 2.6)
project (DataStore)
add_executable(DataStore DataStore.cpp DataStoreTestManager.cpp)
set (CMAKE_CXX_FLAGS "-std=c++11")
