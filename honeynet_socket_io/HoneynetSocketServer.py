# A socket server which redirect arbitrary input from one socket client to another.
"""
This is a coding task of the Honeynet project,
This module is a socket server which read arbitrary input
from one socket and write it to another socket
Actually it is not ``arbitrary input'', because the server handles
TCP protocol only.
This server communicates with multiple clients including:
    receiver clients, and
    sender clients
It reads input from sender clients, and send it to receiver clients.
"""

__author__ = 'yuanchun'

import logging
import socket
import threading
import optparse
import sys
import HoneynetSocketUtil
from HoneynetSocketExceptions import UnknownRequestException

logging.basicConfig(level=logging.DEBUG)


class HoneynetSocketServer(object):
    """
    A socket server which redirect arbitrary input
    from one socket client to another.
    """
    def __init__(self, addr):
        self.addr = addr
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        # set addr reusable
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.is_running = False
        self.logger = logging.getLogger("HoneynetSocketServer")

        # sockets of receiver clients
        self.receiver_socks = set()

    def start(self):
        """
        start the server
        after server started, it should loop to handle multiple client request
        """
        self.sock.bind(self.addr)
        self.sock.listen(5)
        self.is_running = True
        try:
            while self.is_running:
                client_sock, client_addr = self.sock.accept()

                # use threads to handle concurrent requests
                handle_thread = threading.Thread(
                    target=self.handle, args=[client_sock, client_addr])

                # set threads as daemon
                # otherwise server will not stop at KeyboardInterrupt
                handle_thread.setDaemon(True)
                handle_thread.start()
        finally:
            self.stop()
            sys.exit(0)

    def stop(self):
        """
        stop the server
        """
        self.is_running = False
        self.sock.close()

    def handle(self, client_sock, client_addr):
        """
        handle the connection with one client
        :param client_sock: socket connect with client
        :param client_addr: (host_name, port)
        """
        first_op = client_sock.recv(HoneynetSocketUtil.BUF_SIZE)

        # if it is a sender thread, keep listening to the socket
        #  and redirect the data to receiver threads
        if first_op == HoneynetSocketUtil.SENDER_OP:
            client_sock.sendall(HoneynetSocketUtil.RET_SUCCESS)
            self.logger.info("Identify as Sender: %s", client_addr)
            while self.is_running:
                data = client_sock.recv(HoneynetSocketUtil.BUF_SIZE)
                if data:
                    self.logger.debug("From %s, Received:%s", client_addr, data)
                    for receiver_sock in self.receiver_socks:
                        try:
                            receiver_sock.sendall(data)
                        except:
                            pass

        # if it is a receiver thread, simply add the socket to our receiver sockets set
        elif first_op == HoneynetSocketUtil.RECEIVER_OP:
            self.receiver_socks.add(client_sock)
            self.logger.info("Identify as Receiver: %s", client_addr)
            client_sock.sendall(HoneynetSocketUtil.RET_SUCCESS)
        else:
            client_sock.sendall(HoneynetSocketUtil.RET_FAIL)
            client_sock.close()
            raise UnknownRequestException


def parse_args():
    """
    parse command line input
    generate options including host name, port number
    """
    usage = "python HoneynetSocketServer.py -H <host> -p <port>\n"
    usage += "eg. python HoneynetSocketServer.py -H localhost -p 12345"
    parser = optparse.OptionParser(usage=usage)
    parser.add_option("-H", action="store", dest="host", nargs=1,
                      type="string", help="server host")
    parser.add_option("-p", action="store", dest="port", nargs=1,
                      type="int", help="server port")
    options, args = parser.parse_args()
    if len(args) > 0:
        parser.error("command error")
    if not (options.host and options.port):
        parser.error("command error")
    return options


def main(opts):
    """
    the main function
    it starts a HoneynetSocketServer according to
     the host and port given in opts
    :param opts: the options parsed by parse_args()
    """
    addr = (opts.host, opts.port)
    server = HoneynetSocketServer(addr)
    server.start()

if __name__ == '__main__':
    try:
        sys.exit(main(parse_args()))
    except KeyboardInterrupt:
        sys.exit(0)
