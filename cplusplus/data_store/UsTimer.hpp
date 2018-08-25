
#ifndef USTIMER_HPP
#define USTIMER_HPP


#include <chrono>
#include "PrintMacros.hpp"


class UsTimer {
public:
  UsTimer(std::string i):identifier(i),
    start_tp(std::chrono::high_resolution_clock::now()) {
  };
  ~UsTimer() {
    std::chrono::high_resolution_clock::time_point end_tp =
      std::chrono::high_resolution_clock::now();
    long diff =
      std::chrono::duration_cast < std::chrono::microseconds >
      (end_tp - start_tp).count();
    LOG_INFO("Time elapsed for task '" + identifier + "': " +
             std::to_string(diff) + "us");
  };

private:
  UsTimer(void);
  std::string identifier;
  std::chrono::high_resolution_clock::time_point start_tp;
};

#endif
