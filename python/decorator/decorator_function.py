#!/usr/bin/env python2
# pylint: disable=fixme, line-too-long

import logging
import platform


logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.DEBUG)
_log = logging.getLogger(__name__)


class function_only_on(object):
    def __init__(self, pf_name):
        _log.debug("in {} object __init__".format(self.__class__.__name__))
        self._platform = pf_name

    def __call__(self, func):
        _log.debug("in {} object __call__".format(self.__class__.__name__))

        def _wrapper(*args):
            _log.info("enter _wrapper | {}".format(func))

            if self._platform.lower() in platform.platform().lower():
                _log.info("call function {} on this "
                          "platform ({})".format(func, self._platform))
                func(*args)

            _log.info("leave _wrapper | {}".format(func))

        return _wrapper


class TheStuff(object):
    def __init__(self):
        _log.info("hello from a {} object ({})".format(self.__class__.__name__,
                                                       id(self)))

    def use_mouse(self):
        args = None
        _log.info(" >> hello from function 'use_mouse' ({})".format(args))

    @function_only_on("linux")
    def use_alsa(self, nr_0):
        args = [nr_0]
        _log.info(" >> hello from function 'use_alsa' ({})".format(args))

    @function_only_on("linux")
    def use_x(self, nr_0, nr_1):
        args = [nr_0, nr_1]
        _log.info(" >> hello from function 'use_cli' ({})".format(args))

    @function_only_on("linux")
    def use_cli(self):
        args = None
        _log.info(" >> hello from function 'use_cli' ({})".format(args))

    @function_only_on("windows")
    def random_blue_screen(self, nr_0, nr_1, nr_2):
        args = [nr_0, nr_1, nr_2]
        _log.info(" >> hello from function 'blue_screen' {}".format(args))


if __name__ == "__main__":
    _log.info("hello from {}".format(__name__))

    the_stuff = TheStuff()
    the_stuff.use_mouse()
    the_stuff.use_alsa(12)
    the_stuff.use_x(12, 11)
    the_stuff.use_cli()
    the_stuff.random_blue_screen(12, 123, 31)

    _log.info("exit {} gracefully".format(__name__))
