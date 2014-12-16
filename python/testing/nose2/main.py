#!/usr/bin/env python

import class_a
import class_b

import unittest2


class MyUnittestRunner(object):

    def __init__(self):
        self._suite = unittest2.TestSuite()
        self._suite.addTest(unittest2.makeSuite(class_a.ClassATest))
        self._suite.addTest(unittest2.makeSuite(class_b.ClassBTest, 'new_test_'))
        
    def run_test(self):
        print "[MyUnittestRunner] in run"
        print "tests:"
        print self._suite._tests
        print "nr of test cases:"
        print self._suite.countTestCases()

        # stream=None, descriptions=True, verbosity=1, failfast=False, buffer=False, resultclass=None, warnings=None
        my_runner = unittest2.TextTestRunner(verbosity=2, failfast=False)
        test_result = my_runner.run(self._suite)

        print "test result:"
        print type(test_result)
        print "was successful:"
        print test_result.wasSuccessful()


if __name__ == '__main__':

    my_unittest_runner = MyUnittestRunner()
    my_unittest_runner.run_test()