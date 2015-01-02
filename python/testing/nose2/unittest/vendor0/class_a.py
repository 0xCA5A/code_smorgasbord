
import unittest2
import sys
import linux_env_accessor


class ClassA(object):

    def __init__(self):
        print "[ClassA] hello from ClassA constructor (%s)" % (repr(self))

    def __del__(self):
        print "[ClassA] hello from ClassA destructor (%s)" % (repr(self))

    def rock(self):
        print "[ClassA] rock on object %s" % (repr(self))


class ClassATest(unittest2.TestCase):

    def setUp(self):
        print "[ClassATest] in setUp"
        self.class_a_obj = ClassA()

        print sys.path
        import os
        print os.getcwd()



    def tearDown(self):
        print "[ClassATest] in tearDown"

        environment = linux_env_accessor.LinuxEnvAccessor.get()
        print environment


    def env_test_class_a_test_1(self):
        print "[ClassATest] in evn_test_class_a_test_1"
        self.class_a_obj.rock()





if __name__ == '__main__':
    unittest2.main()
