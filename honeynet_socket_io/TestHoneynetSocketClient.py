import socket

__author__ = 'yuanchun'

import SocketServer
import unittest
import threading
import time
import types
import HoneynetSocketUtil
from HoneynetSocketClient import SocketClient, SenderSocketClient, ReceiverSocketClient
import HoneynetSocketExceptions

class Config(object):
    BUF_SIZE = 1024
    CUSTOM_MSG = "This is a customized message!"
    # the message which server should return
    RETURN_MSG = ""

"""
TCP request handler used for testing, it returns the message we defined in RETURN_MSG
"""
class DummyTCPRequestHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        while True:
            data = self.request.recv(Config.BUF_SIZE)
            print "Server received " + data
            if not data:
                return
            if Config.RETURN_MSG == "":
                print "Server sended " + data
                self.request.sendall(data)
            elif type(Config.RETURN_MSG) == types.StringType:
                print "Server sended " + Config.RETURN_MSG
                self.request.sendall(Config.RETURN_MSG)
            elif type(Config.RETURN_MSG) == types.ListType:
                for msg in Config.RETURN_MSG:
                    print "Server sended " + msg
                    self.request.sendall(msg)

class DummyTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    pass

DummyTCPServer.allow_reuse_address = True

def start_server(addr, handler_class):
    server = DummyTCPServer(addr, handler_class)
    server_thread = threading.Thread(target=server.serve_forever)
    server_thread.daemon = True
    server_thread.start()
    time.sleep(1)
    return server

class TestSocketClient(unittest.TestCase):
    def setUp(self):
        """
        start several servers for testing
        """
        print "setUp"
        self.server_addr = ("127.0.0.1", 12345)
        self.server = start_server(self.server_addr, DummyTCPRequestHandler)
        self.server_addr_not_running = ("localhost", 54321)

    def tearDown(self):
        """
        shut down servers
        """
        print "tearDown"
        self.server.shutdown()
        self.server.socket.close()

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

def suite():
    suite = unittest.TestSuite()
    suite.addTest(TestSocketClient())
    suite.addTest(TestSenderClient())
    suite.addTest(TestReceiverClient())
    return suite

if __name__ == '__main__':
    unittest.main()