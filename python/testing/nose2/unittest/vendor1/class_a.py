import unittest2


class ClassA(object):

    def __init__(self):
        print "[ClassA] hello from ClassA constructor (%s)" % (repr(self))

    def __del__(self):
        print "[ClassA] hello from ClassA destructor (%s)" % (repr(self))

    def rock(self):
        print "[ClassA] rock on object %s" % (repr(self))

    def rock_on(self, count=2):
        for counter in range(count):
            print "[ClassA] rock on object %s, counter: %d" % (repr(self), counter)


class ClassATest(unittest2.TestCase):

    def setUp(self):
        print "[ClassATest] in setUp"
        self.class_a_obj = ClassA()

    def tearDown(self):
        print "[ClassATest] in tearDown"

    def test_class_a_test_1(self):
        print "[ClassATest] in test_class_a_test_1"
        self.class_a_obj.rock()

    def new_test_class_a_test_2(self):
        print "[ClassATest] in test_class_a_test_2"
        self.class_a_obj.rock_on(5)
