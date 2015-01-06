#!/bin/bash

echo "***************************************************************"
echo -e "[INFO] set environment variable"
export SW_HASH="79825031da814f4a52d1883e6b87a3e43174ea5d"
export REVISION_STRING="3.5.6-HEAD"
export MAGIC_NUMBER="128"
echo "***************************************************************"

echo "***************************************************************"

nose2 -s unittests mega_feature_2000.MegaFeature2000TestCase.mega_test_feature_1 mega_feature_2000.MegaFeature2000TestCase.mega_test_feature_3

echo "[INFO] nose2 return value: $?"

echo "***************************************************************"


