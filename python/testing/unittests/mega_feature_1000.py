import unittest2
import logging
import lib.linux_env_accessor


# configure module logger, default log level is configured to info
logging.basicConfig()
logger = logging.getLogger()
logger.setLevel(logging.INFO)


class MegaFeature1000(object):

    def __init__(self):
        logger.info("hello from constructor (%s)" % (repr(self)))

    def __del__(self):
        logger.info("hello from destructor (%s)" % (repr(self)))

    def rock(self, number):
        logger.info("rock on object %s: %d" % (repr(self), number))


class MegaFeature1000TestCase(unittest2.TestCase):

    @classmethod
    def setUpClass(cls):
        logger.info("set up class")
        MegaFeature1000TestCase._environment = lib.linux_env_accessor.LinuxEnvAccessor.get()

    @classmethod
    def tearDownClass(cls):
        logger.info("tear down class")

    def setUp(self):
        logger.info("hello from test setup")
        self._obj = MegaFeature1000()

    def tearDown(self):
        logger.info("hello from test teardown")

    def mega_test_feature_1(self):
        logger.info("feature 1 test")
        logger.info(self._environment)
        self._obj.rock(1)

    def vendor0_mega_test_feature_0(self):
        logger.info("vendor0 mega test feature 0")
        logger.info(self._environment)
        self._obj.rock(3)

    def vendor1_mega_test_feature_0(self):
        logger.info("vendor0 mega test feature 0")
        logger.info(self._environment)
        self._obj.rock(3)


if __name__ == '__main__':
    unittest2.main()
