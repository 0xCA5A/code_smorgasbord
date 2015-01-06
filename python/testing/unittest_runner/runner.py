#!/usr/bin/env python

import unittest2
import logging
import os

import unittests.mega_feature_1000
import unittests.mega_feature_2000
import unittests.mega_feature_4000

logger = logging.getLogger()
logger = logging.getLogger()
logger.setLevel(logging.ERROR)


class MyUnittestRunner(object):

    def __init__(self):
        self.suite = unittest2.TestSuite()
        self.suite.addTest(unittest2.makeSuite(unittests.mega_feature_1000.MegaFeature1000TestCase, prefix='vendor1_'))
        self.suite.addTest(unittest2.makeSuite(unittests.mega_feature_2000.MegaFeature2000TestCase, prefix='mega_test_'))
        self.suite.addTest(unittest2.makeSuite(unittests.mega_feature_4000.MegaFeature4000TestCase, prefix='vendor1_'))

        # TODO(casasam): try discovery


    def run_test(self):
        logger.info("lets run some tests")

        logger.info(self.suite._tests)
        logger.info("nr of test cases: %d", self.suite.countTestCases())

        # TextTestRunner: stream=None, descriptions=True, verbosity=1, failfast=False, buffer=False, resultclass=None, warnings=None
        my_runner = unittest2.TextTestRunner(verbosity=2, failfast=False)
        test_result = my_runner.run(self.suite)

        logger.info("test result: %s", repr(test_result))
        if test_result.wasSuccessful():
            logger.info("was successful")
        else:
            logger.error("was not successful")


if __name__ == '__main__':

    # set environment variable
    os.environ['SW_HASH'] = "3c5848d2595617a50aa7c29dedd9fc3a6f2f4ff1"
    os.environ['REVISION_STRING'] = "1.2.4-alpha"
    os.environ['MAGIC_NUMBER'] = "42"

    my_unittest_runner = MyUnittestRunner()
    my_unittest_runner.run_test()

    # python -m unittest test_module1 test_module2
    # python -m unittest test_module.TestClass
    # python -m unittest test_module.TestClass.test_method