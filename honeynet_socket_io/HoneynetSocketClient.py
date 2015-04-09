# Socket client classes
# Including sender client and receiver client class, and a base socket client
"""
This module contains three classes:
SocketClient, SenderSocketClient, and ReceiverSocketClient
These clients are designed to interact with HoneynetSocketServer
SenderSocketClient send messages to HoneynetSocketServer
ReceiverSocketClient receive messages from HoneynetSocketServer
"""

__author__ = 'yuanchun'

import socket
import logging
import sys
import optparse
import HoneynetSocketUtil
from HoneynetSocketExceptions import UnableToConnectException, DenyReceiverException,\
    DenySenderException, UnknownResponseException

logging.basicConfig(level=logging.DEBUG)


class SocketClient(object):
    """
    A basic socket client.
    """
    def __init__(self, server_addr):
        self.server = server_addr
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)
        self.type = "unknown"
        self.is_connected = False
        self.logger = logging.getLogger("SocketClient")

    def init_connect(self):
        """
        set up socket connections with server
        """
        try:
            self.sock.connect(self.server)
        except Exception, err:
            self.logger.error(err)
            raise UnableToConnectException
        self.is_connected = True
        self.logger.info("Connected to %s:%s", self.server[0], self.server[1])

    def disconnect(self):
        """
        close socket with server
        """
        self.is_connected = False
        self.sock.close()


class SenderSocketClient(SocketClient):
    """
    A socket client that sends data to server
    """
    def __init__(self, server_addr):
        super(SenderSocketClient, self).__init__(server_addr)
        self.logger = logging.getLogger("SenderSocketClient")
        self.type = "sender"

    def connect(self):
        """
        connect to server as a sender client
        """
        self.init_connect()

        self.sock.sendall(HoneynetSocketUtil.SENDER_OP)
        response = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)

        if response == HoneynetSocketUtil.RET_SUCCESS:
            self.logger.info("Connected as Sender")
        elif response == HoneynetSocketUtil.RET_FAIL:
            self.disconnect()
            raise DenySenderException
        else:
            self.disconnect()
            raise UnknownResponseException

    def send_message(self, msg):
        """
        send message to server
        """
        if msg:
            self.sock.sendall(msg)

    def start_send_message(self):
        """
        start interacting with user
        send user's input to server
        """
        while self.is_connected:
            message = raw_input("input>")
            self.send_message(message)


class ReceiverSocketClient(SocketClient):
    """
    A socket client that receive data from server
    """
    def __init__(self, server_addr):
        super(ReceiverSocketClient, self).__init__(server_addr)
        self.logger = logging.getLogger("ReceiverSocketClient")
        self.type = "receiver"

    def connect(self):
        """
        connect to server as a receiver client
        :return: void
        """
        self.init_connect()

        self.sock.sendall(HoneynetSocketUtil.RECEIVER_OP)
        response = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)

        if response == HoneynetSocketUtil.RET_SUCCESS:
            self.logger.info("Connected as Receiver")
        elif response == HoneynetSocketUtil.RET_FAIL:
            self.disconnect()
            raise DenyReceiverException
        else:
            self.disconnect()
            raise UnknownResponseException

    def receive_message(self):
        """
        receive a message from server
        this method is blocked
        :return: the message returned
        """
        msg = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)
        return msg

    def wait_for_message(self):
        """
        wait and receive each message sent from server
        :return:
        """
        while self.is_connected:
            response = self.receive_message()
            if response:
                self.logger.debug("Received:%s", response)


def parse_args():
    """
    parse command line input
    generate options including host name, port number, socket type
    """
    usage = "python HoneynetSocketClient.py -H <host> -p <port> -t <type>\n"
    usage += "eg. python HoneynetSocketClient.py -H localhost -p 12345 -t sender"
    parser = optparse.OptionParser(usage=usage)
    parser.add_option("-H", action="store", dest="host", nargs=1,
                      type="string", help="server host")
    parser.add_option("-p", action="store", dest="port", nargs=1,
                      type="int", help="server port")
    parser.add_option("-t", action="store", dest="type", nargs=1,
                      type="string", help="client type (sender/receiver)")
    opts, args = parser.parse_args()
    if len(args) > 0:
        parser.error("command error")
    if not (opts.host and opts.type and opts.port):
        parser.error("command error")
    if opts.type != "sender" and opts.type != "receiver":
        parser.error("command error")
    return opts


def main(opts):
    """
    main function
    start socket client according to command line input
    :param opts: options parsed by parse_args()
    """
    if opts.type == "sender":
        client = SenderSocketClient((opts.host, opts.port))
        client.connect()
        client.start_send_message()
    elif opts.type == "receiver":
        client = ReceiverSocketClient((opts.host, opts.port))
        client.connect()
        client.wait_for_message()
    else:
        return

if __name__ == '__main__':
    try:
        sys.exit(main(parse_args()))
    except KeyboardInterrupt:
        sys.exit(0)
    finally:
        sys.exit(1)
