import unittest


class ClassB(object):

    def __init__(self):
        print "[ClassB] hello from ClassB constructor (%s)" % (repr(self))

    def __del__(self):
        print "[ClassB] hello from ClassB destructor (%s)" % (repr(self))

    def rock(self):
        print "[ClassB] rock on object %s" % (repr(self))
        return 1

    def rock_on(self, count=2):
        for counter in range(count):
            print "[ClassB] rock on object %s, counter: %d" % (repr(self), counter)
        return count


class ClassBTest(unittest.TestCase):

    def setUp(self):
        print "[ClassBTest] in setUp"
        self.class_b_obj = ClassB()

    def tearDown(self):
        print "[ClassBTest] in tearDown"

    def test_class_b_test_1(self):
        print "[ClassBTest] in test_class_b_test_1"
        result = self.class_b_obj.rock()
        self.assertEquals(result, 1)

    def new_test_class_b_test_2(self):
        print "[ClassATest] in test_class_b_test_2"
        counter = 5
        result = self.class_b_obj.rock_on(counter)
        self.assertEquals(counter, result)

    def new_test_class_b_test_3(self):
        print "[ClassATest] in test_class_b_test_3"
        counter = 7
        result = self.class_b_obj.rock_on(counter)
        self.assertEquals(counter, result)

    def new_test_class_b_test_4(self):
        print "[ClassATest] in test_class_b_test_4"
        counter = 1
        result = self.class_b_obj.rock_on(counter)
        self.assertEquals(counter, not result)

    def new_test_class_b_test_5(self):
        print "[ClassATest] in test_class_b_test_5"
        counter = 4
        result = self.class_b_obj.rock_on(counter)
        self.assertEquals(counter, result)


if __name__ == '__main__':
    unittest.main()
