# Unit Test of HoneynetSocketClient
# Including test cases of class SocketClient, SenderSocketClient, ReceiverSocketClient

"""
This module provides test cases of HoneynetSocketClient.
This is a black box testing.
It starts a dummy TCP server which can return whatever we defined,
and assert that whether the clients functions well in different situation.
DummyTCPServer is the class we used to create dummy server.
TestSocketClient, TestSenderClient, and TestReceiverClient contains the test cases.
"""
__author__ = 'yuanchun'

import SocketServer
import unittest
import threading
import types
import HoneynetSocketUtil
import HoneynetSocketExceptions
from HoneynetSocketClient import SocketClient, SenderSocketClient, ReceiverSocketClient


class Config(object):
    """
    Shared variable used in TestHoneynetSocketServer
    """
    BUF_SIZE = 1024
    CUSTOM_MSG = "This is a customized message!"
    # the message which server should return
    RETURN_MSG = ""


class DummyTCPRequestHandler(SocketServer.BaseRequestHandler):
    """
    TCP request handler used for testing, it returns the message we defined in RETURN_MSG
    """

    def handle(self):
        """
        handle requests from client.
        the response it returns is defined in Config.RETURN_MSG
        if RETURN_MSG is empty, it echos client inputs
        if RETURN_MSG is string, it returns RETURN_MSG
        if RETURN_MSG is array, it returns elements in the array one by one
        :return: void
        """
        while True:
            data = self.request.recv(Config.BUF_SIZE)
            if not data:
                return
            if Config.RETURN_MSG == "":
                self.request.sendall(data)
            elif isinstance(Config.RETURN_MSG, types.StringType):
                self.request.sendall(Config.RETURN_MSG)
            elif isinstance(Config.RETURN_MSG, types.ListType):
                for msg in Config.RETURN_MSG:
                    self.request.sendall(msg)


class DummyTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    """
    the class we used to create dummy server
    """
    pass

# set allow_reuse_address to True,
# so that the socket address used by DummyTCPServer is reusable
DummyTCPServer.allow_reuse_address = True


def start_server(addr, handler_class):
    """
    A helper function,
    which start a threaded server according to the given handler class
    :param addr: (host, port)
    :param handler_class: RequestHandler
    :return: DummyTCPServer
    """
    server = DummyTCPServer(addr, handler_class)
    server_thread = threading.Thread(target=server.serve_forever)
    server_thread.daemon = True
    server_thread.start()
    return server


class TestSocketClient(unittest.TestCase):
    """
    test SocketClient class
    """

    def setUp(self):
        """
        start several servers for testing
        """
        self.server_addr = ("127.0.0.1", 12345)
        self.server = start_server(self.server_addr, DummyTCPRequestHandler)
        self.server_addr_not_running = ("localhost", 54321)

    def tearDown(self):
        """
        shut down servers
        """
        self.server.socket.close()
        self.server.shutdown()

    def test_init_connect(self):
        """
        test init_connect function in class SocketClient
        :return:
        """
        # test that client can connect to a right server
        client = SocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        client.init_connect()
        self.assertTrue(client.is_connected)
        client.disconnect()
        self.assertFalse(client.is_connected)

        # test that client can not connect to a wrong server, and will raise a Exception
        client = SocketClient(self.server_addr_not_running)
        self.assertFalse(client.is_connected)
        with self.assertRaises(HoneynetSocketExceptions.UnableToConnectException):
            client.init_connect()
        self.assertFalse(client.is_connected)


class TestSenderClient(TestSocketClient):
    """
    test SenderSocketClient class
    """

    def test_connect(self):
        """
        test client function connect() in class SenderSocketClient
        :return:
        """
        # test that the client successfully connects to a server which returns SUCCESS
        Config.RETURN_MSG = HoneynetSocketUtil.RET_SUCCESS
        client = SenderSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        client.connect()
        self.assertTrue(client.is_connected)
        client.disconnect()
        self.assertFalse(client.is_connected)

        # test that the client fails to connect to a server which returns FAIL
        Config.RETURN_MSG = HoneynetSocketUtil.RET_FAIL
        client = SenderSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        with self.assertRaises(HoneynetSocketExceptions.DenySenderException):
            client.connect()
        self.assertFalse(client.is_connected)

        # test that the client fails to connect to a server which returns unknown message
        Config.RETURN_MSG = "This is an unknown message!"
        client = SenderSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        with self.assertRaises(HoneynetSocketExceptions.UnknownResponseException):
            client.connect()
        self.assertFalse(client.is_connected)

    def test_send(self):
        """
        test send_message() in class SenderSocketClient
        :return:
        """
        # send something to echo server, it should return the same thing
        Config.RETURN_MSG = HoneynetSocketUtil.RET_SUCCESS
        client = SenderSocketClient(self.server_addr)
        client.connect()
        self.assertTrue(client.is_connected)
        msg = "something"
        Config.RETURN_MSG = ""
        client.send_message(msg)
        ret_msg = client.sock.recv(Config.BUF_SIZE)

        self.assertEqual(msg, ret_msg)
        client.disconnect()


class TestReceiverClient(TestSocketClient):
    """
    test ReceiverClient class
    """

    def test_connect(self):
        """
        test client function connect() in class ReceiverSocketClient
        :return:
        """
        # test that the client successfully connects to a server which returns SUCCESS
        Config.RETURN_MSG = HoneynetSocketUtil.RET_SUCCESS
        client = ReceiverSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        client.connect()
        self.assertTrue(client.is_connected)
        client.disconnect()
        self.assertFalse(client.is_connected)

        # test that the client fails to connect to a server which returns FAIL
        Config.RETURN_MSG = HoneynetSocketUtil.RET_FAIL
        client = ReceiverSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        with self.assertRaises(HoneynetSocketExceptions.DenyReceiverException):
            client.connect()
        self.assertFalse(client.is_connected)

        # test that the client fails to connect to a server which returns unknown message
        Config.RETURN_MSG = "This is an unknown message!"
        client = ReceiverSocketClient(self.server_addr)
        self.assertFalse(client.is_connected)
        with self.assertRaises(HoneynetSocketExceptions.UnknownResponseException):
            client.connect()
        self.assertFalse(client.is_connected)

    def test_receive(self):
        """
        test receive_message() in class ReceiverSocketClient
        :return:
        """
        # send something to custom server, it should return the customized string
        Config.RETURN_MSG = [HoneynetSocketUtil.RET_SUCCESS, Config.CUSTOM_MSG]
        client = ReceiverSocketClient(self.server_addr)
        client.connect()
        self.assertTrue(client.is_connected)
        self.assertEqual(client.receive_message(), Config.CUSTOM_MSG)
        client.disconnect()


class TestCommandLineParser(unittest.TestCase):
    """
    Test whether the parse_args function functions well
    """
    def test_parse_args(self):
        """
        pass string to parse_args function and
        assert whether options are correctly parsed
        """
        cmd = ""

if __name__ == '__main__':
    unittest.main()

