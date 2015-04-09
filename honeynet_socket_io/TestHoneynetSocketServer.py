# Unit Test of HoneynetSocketServer
# Including some basic unit test and one pressure test

"""
This module provides test cases of HoneynetSocketServer.
This is a black box testing.
It tests HoneynetSocketServer by letting several dummy socket clients
interact with HoneynetSocketServer started.
DummyTCPClient is the class we used to create dummy clients.
TestHoneynetSocketServer contains the testcase.

Methods in TestHoneynetSocketServer:
    sender_connect(), receiver_connect(), socket_disconnect():
        These are util functions for connecting and disconnecting socket.
    test_basic() is a basic black box test.
        It starts a sender and a receiver and assert the sent message
        equals to the received message
    test_pressure() is a pressure test.
        It starts senders and receivers amount of PRESSURE. and assert
        the received messages are connect.

Function:
    receive_right() helps to judge whether the received messages are correct.
"""
import signal

__author__ = 'yuanchun'

import unittest
import socket
import os
import sys
import HoneynetSocketUtil
from HoneynetSocketServer import HoneynetSocketServer


class Config(object):
    """
    Shared variable used in TestHoneynetSocketServer
    """
    BUF_SIZE = 1024
    MSG = "honey"
    SERVER_ADDR = ("localhost", 12345)
    SERVER = HoneynetSocketServer(SERVER_ADDR)
    # used for pressure test
    PRESSURE = 100


class DummyTCPClient(object):
    """
    A dummy client for testing
    """
    def __init__(self, server_addr):
        self.server = server_addr
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)

    def connect(self):
        """
        connect client socket with its server socket
        """
        self.sock.connect(self.server)

    def disconnect(self):
        """
        disconnect client socket with its server socket
        """
        self.sock.close()

    def connect_and_send(self, op_code):
        """
        connect client socket with its server socket
        and send a OP-CODE after connected
        :return: True if Server returns SUCCESS, False otherwise
        """
        self.connect()
        self.sock.sendall(op_code)
        ret = self.sock.recv(Config.BUF_SIZE)
        if ret == HoneynetSocketUtil.RET_SUCCESS:
            return True
        return False

class TestHoneynetSocketServer(unittest.TestCase):
    """
    The testcase of HoneynetSocketServer
    """
    def setUp(self):
        """
        create dummy clients we will use later.
        but do not start them.
        :return:
        """
        self.sender = DummyTCPClient(Config.SERVER_ADDR)
        self.receiver = DummyTCPClient(Config.SERVER_ADDR)
        self.unknown_client = DummyTCPClient(Config.SERVER_ADDR)

        self.senders = []
        self.receivers = []
        count = 0
        while count < Config.PRESSURE:
            self.senders.append(DummyTCPClient(Config.SERVER_ADDR))
            self.receivers.append(DummyTCPClient(Config.SERVER_ADDR))
            count += 1

    def test_basic(self):
        """
        test basic feature of HoneynetSocketServer
        :return:
        """
        # start a sender and a receiver, assert that the data sender sends is received by receiver
        ret = self.unknown_client.connect_and_send("unknown op")
        self.assertFalse(ret)
        ret = self.sender.connect_and_send(HoneynetSocketUtil.SENDER_OP)
        self.assertTrue(ret)
        ret = self.receiver.connect_and_send(HoneynetSocketUtil.RECEIVER_OP)
        self.assertTrue(ret)

        msg_sent = Config.MSG
        self.sender.sock.sendall(msg_sent)
        msg_received = self.receiver.sock.recv(Config.BUF_SIZE)
        self.assertEqual(msg_sent, msg_received)
        self.sender.disconnect()
        self.receiver.disconnect()

    def test_pressure(self):
        """
        pressure test of HoneynetSocketServer
        :return: void
        """
        # start some senders
        sender_connect = (lambda sender:
                          sender.connect_and_send(HoneynetSocketUtil.SENDER_OP))
        map(sender_connect, self.senders)
        # start some receivers
        receiver_connect = (lambda receiver:
                            receiver.connect_and_send(HoneynetSocketUtil.RECEIVER_OP))
        map(receiver_connect, self.receivers)
        msg_sent = Config.MSG
        # let senders send msgs
        send_message = (lambda sender: sender.sock.sendall(msg_sent))
        map(send_message, self.senders)
        # assert in each receivers that it received the correct messages
        assert_receiver = (lambda receiver: self.assertTrue(receive_right(receiver)))
        map(assert_receiver, self.receivers)
        # disconnect all sockets
        socket_disconnect = (lambda sock: sock.disconnect)
        map(socket_disconnect, self.senders)
        map(socket_disconnect, self.receivers)

def receive_right(receiver):
    """
    assert that the receiver receives right messages
    in this case, it should receive MSG with PRESSURE times.
    :param receiver: DummyTCPClient
    :return: boolean
    """
    count = 0
    while True:
        data = receiver.sock.recv(Config.BUF_SIZE)
        if data:
            substr_count = data.count(Config.MSG)
            if substr_count == 0:
                return False
            count += substr_count
        if count == Config.PRESSURE:
            receiver.sock.close()
            return True

if __name__ == '__main__':
    try:
        pid = os.fork()
        if pid != 0:
            unittest.main(exit=False)
            # after unittest, stop server
            os.kill(pid, signal.SIGINT)
            sys.exit(0)
        else:
            Config.SERVER.start()
    finally:
        Config.SERVER.stop()

