


#http://stackoverflow.com/questions/576169/understanding-python-super-and-init-methods
#http://www.logilab.org/6883


import sys


class MyNothing(object):

    def __init__(self):
        print "[%s] hello from %s object" % (sys._getframe().f_code.co_name, self.__class__.__name__)


class MyClass(object):

    def __init__(self):
        print "[%s] hello from %s object" % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.__unused_list = []
        self._unused_number = 42

    def __local_hello(self):
        print "\t[%s] >> say hello from %s"  % (sys._getframe().f_code.co_name, self.__class__.__name__)

    def global_hello(self):
        print "[%s] hello from %s"  % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.__local_hello()


class MyClass2000(MyClass):

    def __init__(self):
        MyClass.__init__(self)
        #super(MyClass2000, self).__init__()
        print "[%s] hello from %s object" % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.my_nothing = MyNothing()

    def global_hello_2000(self):
        print "[%s] hello from %s"  % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.global_hello()


class MyClass3000(MyClass):

    def __init__(self):
        #super().__init__(self) -> Python 3.0
        super(MyClass3000, self).__init__()
        print "[%s] hello from %s object" % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.__unused_dict = {}


    def global_hello_3000(self):
        print "[%s] hello from %s"  % (sys._getframe().f_code.co_name, self.__class__.__name__)
        self.global_hello()