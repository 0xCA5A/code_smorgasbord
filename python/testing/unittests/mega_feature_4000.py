import unittest2
import logging
import lib.linux_env_accessor


# configure module logger, default log level is configured to info
logging.basicConfig()
logger = logging.getLogger()
logger.setLevel(logging.INFO)


class MegaFeature4000(object):

    def __init__(self):
        logger.info("hello from constructor (%s)" % (repr(self)))

    def __del__(self):
        logger.info("hello from destructor (%s)" % (repr(self)))

    def rock(self, number):
        logger.info("rock on object %s: %d" % (repr(self), number))


class MegaFeature4000TestCase(unittest2.TestCase):

    @classmethod
    def setUpClass(cls):
        logger.info("set up class")
        MegaFeature4000TestCase._environment = lib.linux_env_accessor.LinuxEnvAccessor.get()

    @classmethod
    def tearDownClass(cls):
        logger.info("tear down class")

    def setUp(self):
        logger.info("hello from test setup")
        self._obj = MegaFeature4000()

    def tearDown(self):
        logger.info("hello from test teardown")

    def mega_test_feature_1(self):
        logger.info("feature 1 test")
        logger.info(self._environment)
        self._obj.rock(1)

    def mega_test_feature_2(self):
        logger.info("feature 2 test")
        logger.info(self._environment)
        self._obj.rock(2)

    @unittest2.skipIf(int(lib.linux_env_accessor.LinuxEnvAccessor.get()['MAGIC_NUMBER']) > 12, "magic number was too big")
    def mega_test_feature_3(self):
        logger.info("feature 3 test")
        logger.info(self._environment)
        self._obj.rock(3)

    @unittest2.expectedFailure
    def vendor1_mega_test_feature_0(self):
        logger.info("vendor0 mega test feature 0")
        logger.info(self._environment)
        self._obj.rock(3)
        self.assertNotEquals(1, 1)

    def vendor1_mega_test_feature_1(self):
        logger.info("vendor1 mega test feature 1")
        logger.info(self._environment)

        # skip test if condition check failed
        if int(self._environment['MAGIC_NUMBER']) > 10:
            self.skipTest("magic number is too large")

        self._obj.rock(3)

    @unittest2.skipIf(int(lib.linux_env_accessor.LinuxEnvAccessor.get()['MAGIC_NUMBER']) > 12, "magic number was too big")
    def vendor1_mega_test_feature_2(self):
        logger.info("vendor1 mega test feature 2")
        logger.info(self._environment)
        self._obj.rock(3)

    @unittest2.skip("demonstrating skipping")
    def vendor1_mega_test_feature3(self):
        logger.info("vendor1 mega test feature 3")
        logger.info(self._environment)
        self._obj.rock(3)


if __name__ == '__main__':
    unittest2.main()
