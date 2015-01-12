import unittest2
import logging
import lib.linux_env_accessor
from BaseHTTPServer import HTTPServer
import threading


# configure module logger, default log level is configured to info
logging.basicConfig()
logger = logging.getLogger()
logger.setLevel(logging.INFO)


class Feature7(object):

    DEFAULT_SERVER_ADDRESS = ''
    DEFAULT_SERVER_PORT = 8092
    DEFAULT_MAX_MESSAGE_QUEUE_SIZE = 128

    def __init__(self, server_address=DEFAULT_SERVER_ADDRESS,
                 server_port=DEFAULT_SERVER_PORT,
                 message_queue_size=DEFAULT_MAX_MESSAGE_QUEUE_SIZE):
        logger.info("hello from constructor (%s)" % (repr(self)))

        self._server_address = server_address
        self._server_port = server_port

        self._http_request_server = None
        self._http_request_server_thread = None

    def __del__(self):
        logger.info("hello from destructor (%s)" % (repr(self)))

    def _cheap_http_handler(self):
        logging.info("in dummy HTTP server handler")

    def rock(self, number):
        logger.info("rock on object %s: %d" % (repr(self), number))

    def create_http_server_thread(self):
        logger.info("create HTTP server thread")

        self._http_request_server = HTTPServer((self._server_address, self._server_port), self._cheap_http_handler)
        self._http_request_server_thread = threading.Thread(target=self._http_request_server.serve_forever)

    def stop_http_server_thread(self):
        """shutdown the http server, stop the thread

        :return: None
        """
        logger.info("stop HTTP server thread")
        if self._http_request_server:
            # tell the serve_forever() loop to stop and wait until it does
            self._http_request_server.shutdown()
            # close the socket
            self._http_request_server.server_close()

        if self._http_request_server_thread:
            self._http_request_server_thread.join()

    def start_http_server_thread(self):
        """start the http server thread

        :return: None
        """
        logger.info("start HTTP server thread")
        self._http_request_server_thread.start()


class Feature7TestCase(unittest2.TestCase):

    @classmethod
    def setUpClass(cls):
        logger.info("set up class")
        Feature7TestCase._environment = lib.linux_env_accessor.LinuxEnvAccessor.get()
        Feature7TestCase._feature7 = Feature7()

    @classmethod
    def tearDownClass(cls):
        logger.info("tear down class")
        Feature7TestCase._feature7.stop_http_server_thread()

    def setUp(self):
        logger.info("hello from test setup")
        self._obj = Feature7()

    def tearDown(self):
        logger.info("hello from test teardown")
        self._obj.stop_http_server_thread()

    def vendor0_mega_ressource_cleanup_test_object(self):
        logger.info("feature 7 test 0")
        logger.info(self._environment)
        self._obj.rock(1)
        self._obj.create_http_server_thread()
        self._obj.start_http_server_thread()

        logger.info("before failing assertion with running HTTP server thread, no cleanup in test function")
        assert(False)
        Feature7TestCase._feature7.stop_http_server_thread()

    def vendor0_mega_ressource_cleanup_test_static(self):
        Feature7TestCase._feature7.create_http_server_thread()
        Feature7TestCase._feature7.start_http_server_thread()

        logger.info("before failing assertion with running HTTP server thread, no cleanup in test function")
        assert(False)
        Feature7TestCase._feature7.stop_http_server_thread()

if __name__ == '__main__':
    unittest2.main()
