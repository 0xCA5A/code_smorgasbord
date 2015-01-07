#!/bin/bash

echo "********************************************************************************"

set -x

echo -e "[INFO] set environment variable"
export SW_HASH="79825031da814f4a52d1883e6b87a3e43174ea5d"
export REVISION_STRING="3.5.6-HEAD"
export MAGIC_NUMBER="128"

set +x

echo "********************************************************************************"


echo "********************************************************************************"

if [ $# -ne 1 ] ; then
    echo -e "[ERROR] need a config file\n"
    exit 1
fi

nose2 --verbose --config $1

echo "[INFO] nose2 return value: $?"

echo "********************************************************************************"

