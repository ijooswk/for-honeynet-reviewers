import time
from HoneynetSocketServer import HoneynetSocketServer
import HoneynetSocketUtil

__author__ = 'yuanchun'

import unittest
import socket
import threading
import os
import sys
import signal

class Config(object):
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
        self.sock.connect(self.server)
    def disconnect(self):
        self.sock.close()

class TestHoneynetSocketServer(unittest.TestCase):
    def setUp(self):
        self.sender = DummyTCPClient(Config.SERVER_ADDR)
        self.receiver = DummyTCPClient(Config.SERVER_ADDR)

        self.senders = []
        self.receivers = []
        count = 0
        while count < Config.PRESSURE:
            self.senders.append(DummyTCPClient(Config.SERVER_ADDR))
            self.receivers.append(DummyTCPClient(Config.SERVER_ADDR))
            count += 1

    def tearDown(self):
        pass

    def sender_connect(self, sender):
        sender.connect()
        sender.sock.sendall(HoneynetSocketUtil.SENDER_OP)
        ret = sender.sock.recv(Config.BUF_SIZE)
        if ret == HoneynetSocketUtil.RET_SUCCESS:
            return True
        return False
    def receiver_connect(self, receiver):
        receiver.connect()
        receiver.sock.sendall(HoneynetSocketUtil.RECEIVER_OP)
        ret = receiver.sock.recv(Config.BUF_SIZE)
        if ret == HoneynetSocketUtil.RET_SUCCESS:
            return True
        return False
    def socket_disconnect(self, sender):
        sender.disconnect()


    def testBasic(self):
        '''
        test basic feature of HoneynetSocketServer
        :return:
        '''

        # start a sender and a receiver, assert that the data sender sends is received by receiver
        ret = self.sender_connect(self.sender)
        self.assertTrue(ret)
        ret = self.receiver_connect(self.receiver)
        self.assertTrue(ret)
        msg_sended = Config.MSG
        self.sender.sock.sendall(msg_sended)
        msg_received = self.receiver.sock.recv(Config.BUF_SIZE)
        self.assertEqual(msg_sended, msg_received)
        self.socket_disconnect(self.sender)
        self.socket_disconnect(self.receiver)

    def testPressure(self):
        '''
        pressure test of HoneynetSocketServer
        :return:
        '''
        # start some senders
        map(self.sender_connect, self.senders)
        # start some receivers
        map(self.receiver_connect, self.receivers)
        msg_sended = Config.MSG
        # let senders send msgs
        map((lambda sender: sender.sock.sendall(msg_sended)), self.senders)
        # assert in each receivers that it received the correct messages
        map((lambda receiver: self.assertTrue(self.receive_right(receiver))), self.receivers)
        # disconnect all sockets
        map(self.socket_disconnect, self.senders)
        map(self.socket_disconnect, self.receivers)

    def receive_right(self, receiver):
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
        childpid = 0
        pid = os.fork()
        if pid != 0:
            # wait 1 second(s) for testing
            time.sleep(1)
            # kill child processes
            os.kill(pid, signal.SIGINT)
            sys.exit(0)
        else:
            pid = os.fork()
            if pid != 0:
                Config.SERVER.start()
            else:
                unittest.main()
    except Exception as e:
        print e
    finally:
        Config.SERVER.stop()