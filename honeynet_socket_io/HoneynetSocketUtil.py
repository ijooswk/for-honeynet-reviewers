# Global variables and op codes used in
# HoneynetSocketClient and HoneynetSocketServer

__author__ = 'yuanchun'

# the opcodes of sender client and receiver clients
# clients should send op code once after connection established to claim their type
SENDER_OP = "sender"
RECEIVER_OP = "receiver"
HEARTBEAT_OP = "heartbeat"

# responses to client requests, 0 for success, 1 for fail
RET_SUCCESS = "success"
RET_FAIL = "failed"

BUF_SIZE = 1024