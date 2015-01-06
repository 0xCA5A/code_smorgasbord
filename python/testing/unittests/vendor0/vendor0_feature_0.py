import unittest2
import logging
import lib.linux_env_accessor


# configure module logger, default log level is configured to info
logging.basicConfig()
logger = logging.getLogger()
logger.setLevel(logging.INFO)


class Vendor0Feature0(object):

    def __init__(self):
        logger.info("hello from constructor (%s)" % (repr(self)))

    def __del__(self):
        logger.info("hello from destructor (%s)" % (repr(self)))

    def rock(self, number):
        logger.info("rock on object %s: %d" % (repr(self), number))


class Vendor0Feature0TestCase(unittest2.TestCase):

    @classmethod
    def setUpClass(cls):
        logger.info("set up class")
        Vendor0Feature0TestCase._environment = lib.linux_env_accessor.LinuxEnvAccessor.get()

    @classmethod
    def tearDownClass(cls):
        logger.info("tear down class")

    def setUp(self):
        logger.info("hello from test setup")
        self._obj = Vendor0Feature0TestCase()

    def tearDown(self):
        logger.info("hello from test teardown")

    @unittest2.skip("demonstrating skipping")
    def vendor0_test_feature_0(self):
        logger.info("feature 0 test")
        logger.info(self._environment)
        self._obj.rock(1)

    def vendor0_test_feature_1(self):
        logger.info("feature 1 test")
        logger.info(self._environment)
        self._obj.rock(1)

    @unittest2.skipIf(int(lib.linux_env_accessor.LinuxEnvAccessor.get()['MAGIC_NUMBER']) > 12, "magic number was too big")
    def vendor0_mega_test_feature3(self):
        logger.info("vendor1 mega test feature 3")
        logger.info(self._environment)
        self._obj.rock(3)


if __name__ == '__main__':
    unittest2.main()
