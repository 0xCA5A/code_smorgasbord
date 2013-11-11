#!/bin/bash

echo -e "[i] sync"
sync
sync
sync


echo -e "[i] build with O0"
export CPPFLAGS="-O0"
make clean
make -j2

echo -e "[i] run O2 build"
./memoryFlipper | grep -e duration 


echo -e "[i] build with O2"
export CPPFLAGS="-O2"
make clean
make -j2

echo -e "[i] run O2 build"
./memoryFlipper | grep -e duration


echo -e "[i] build with O2"
export CPPFLAGS="-O3"
make clean
make -j2

echo -e "[i] run O3 build"
./memoryFlipper | grep -e duration


echo -e "[i] build with O2"
export CPPFLAGS="-march=core2 -O3 -pipe -fomit-frame-pointer"
make clean
make -j2

echo -e "[i] run guido optimized build"
./memoryFlipper | grep -e duration
