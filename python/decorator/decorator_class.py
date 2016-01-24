#!/usr/bin/env python2
# pylint: disable=fixme, line-too-long

import logging
import platform
import abc


logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.DEBUG)
_log = logging.getLogger(__name__)


class TheStuff(object):
    __metaclass__ = abc.ABCMeta

    def __init__(self):
        _log.info("hello from a {} object ({})".format(self.__class__.__name__,
                                                       id(self)))
    @abc.abstractmethod
    def foo(self, nr_0):
        pass
    @abc.abstractmethod
    def bar(self):
        pass


class OnlyOnWindows(type):
    """class to manage platform restrictions, windows support only here

    use:
      class NormalClass(object):
        __metaclass__ = OnlyForWindows
        ...
    """
    TARGET_PLATFORM = "windows"

    @staticmethod
    def _forbidden(*args, **kwargs):
        _log.fatal("original function only supported on platform '{}', your platform: {}".format(OnlyOnWindows.TARGET_PLATFORM, platform.platform()))

    def __new__(mcs, classname, supers, classdict):
        # check target platform
        if OnlyOnWindows.TARGET_PLATFORM.lower() not in platform.platform().lower():
            for attr, attrval in classdict.items():
                # functions only
                from types import FunctionType
                if type(attrval) is FunctionType:
                    # skip __xxx__ functions
                    if attr.startswith("__") and attr.endswith("__"):
                        continue

                    # remap original function
                    classdict[attr] = OnlyOnWindows._forbidden
                    _log.debug("remap function {} to function {}".format(attrval, OnlyOnWindows._forbidden))

        return type.__new__(mcs, classname, supers, classdict)


class TheStuffWindowsMeta(object):
    __metaclass__ = OnlyOnWindows

    def __init__(self):
        # super(TheStuffWindows, self).__init__()
        _log.info("hello from a {} object ({})".format(self.__class__.__name__, id(self)))

    def foo(self, nr_0):
        args = nr_0
        _log.info(" >> hello from {} object function 'foo' ({})".format(self.__class__.__name__, args))

    def bar(self):
        args = None
        _log.info(" >> hello from {} object function 'foo' ({})".format(self.__class__.__name__, args))

class TheStuffLinux(TheStuff):

    def __init__(self):
        super(TheStuffLinux, self).__init__()
        _log.info("hello from a {} object ({})".format(self.__class__.__name__, id(self)))

    def foo(self, nr_0):
        args = nr_0
        _log.info(" >> hello from {} object function 'foo' ({})".format(self.__class__.__name__, args))

    def bar(self):
        args = None
        _log.info(" >> hello from {} object function 'foo' ({})".format(self.__class__.__name__, args))


if __name__ == "__main__":
    _log.info("hello from {}".format(__name__))

    windows = TheStuffWindowsMeta()
    windows.foo(33)
    windows.bar()

    linux = TheStuffLinux()
    linux.foo(33)
    linux.bar()

    _log.info("exit {} gracefully".format(__name__))
