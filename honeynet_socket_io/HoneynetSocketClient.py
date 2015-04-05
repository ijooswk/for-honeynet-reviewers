import socket
import logging
import sys
import optparse
import HoneynetSocketUtil
from HoneynetSocketExceptions import UnableToConnectException, DenyReceiverException,\
    DenySenderException, UnknownResponseException

__author__ = 'yuanchun'

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

    '''
    connect to server
    '''
    def init_connect(self):
        try:
            self.sock.connect(self.server)
        except:
            raise UnableToConnectException
        self.is_connected = True
        self.logger.info("Connected to %s:%s", self.server[0], self.server[1])

    def disconnect(self):
        self.sock.close()

class SenderSocketClient(SocketClient):
    """
    A socket client that sends data to server
    """
    def __init__(self, server_addr):
        super(SenderSocketClient, self).__init__(server_addr)
        self.logger = logging.getLogger("SenderSocketClient")
        self.type = "sender"

    '''
    connect to server as a sender client
    '''
    def connect(self):
        self.init_connect()
        try:
            self.sock.sendall(HoneynetSocketUtil.SENDER_OP)
            response = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)

            if response == HoneynetSocketUtil.RET_SUCCESS:
                self.logger.info("Connected as Sender")
                self.send_message()
            elif response == HoneynetSocketUtil.RET_FAIL:
                self.sock.close()
                self.is_connected = False
                raise DenySenderException
            else:
                self.sock.close()
                self.is_connected = False
                raise UnknownResponseException
        finally:
            self.sock.close()

    def send_message(self):
        while(self.is_connected):
            message = raw_input("input>")
            if message:
                self.sock.sendall(message)

class ReceiverSocketClient(SocketClient):
    """
    A socket client that sends data to server
    """
    def __init__(self, server_addr):
        super(ReceiverSocketClient, self).__init__(server_addr)
        self.logger = logging.getLogger("ReceiverSocketClient")
        self.type = "receiver"

    '''
    connect to server as a receiver client
    '''
    def connect(self):
        self.init_connect()

        try:
            self.sock.sendall(HoneynetSocketUtil.RECEIVER_OP)
            response = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)

            if response == HoneynetSocketUtil.RET_SUCCESS:
                self.logger.info("Connected as Receiver")
                self.wait_for_message()
            elif response == HoneynetSocketUtil.RET_FAIL:
                self.sock.close()
                self.is_connected = False
                raise DenyReceiverException
            else:
                self.sock.close()
                self.is_connected = False
                raise UnknownResponseException
        finally:
            self.sock.close()

    def wait_for_message(self):
        while self.is_connected:
            response = self.sock.recv(HoneynetSocketUtil.BUF_SIZE)
            if response:
                self.logger.debug("Received:%s", response)

def parse_args():
    usage = "python HoneynetSocketClient.py -H <host> -p <port> -t <type>\n"
    usage += "eg. python HoneynetSocketClient.py -H localhost -p 12345 -t sender"
    parser = optparse.OptionParser(usage=usage)
    parser.add_option("-H", action="store", dest="host", nargs=1, type="string", help="server host")
    parser.add_option("-p", action="store", dest="port", nargs=1, type="int", help="server port")
    parser.add_option("-t", action="store", dest="type", nargs=1, type="string", help="client type (sender/receiver)")
    options, args = parser.parse_args()
    if len(args) > 0:
        parser.error("command error")
    if not (options.host and options.type and options.port):
        parser.error("command error")
    if options.type != "sender" and options.type != "receiver":
        parser.error("command error")
    return options

def main(options):
    if options.type == "sender":
        client = SenderSocketClient((options.host, options.port))
        client.connect()
    elif options.type == "receiver":
        client = ReceiverSocketClient((options.host, options.port))
        client.connect()
    else:
        return

if __name__ == '__main__':
    options = parse_args()
    try:
        sys.exit(main(options))
    except KeyboardInterrupt:
        sys.exit(0)
