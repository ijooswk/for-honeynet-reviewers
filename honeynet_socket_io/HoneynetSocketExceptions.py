# Exceptions used in HoneynetSocketClient and HoneynetSocketServer

__author__ = 'yuanchun'

class UnableToConnectException(Exception):
    """
    Exception for HoneynetSocketClient unable to connect to server
    """
    pass

class DenySenderException(Exception):
    """
    Exception for HoneynetSenderClient when server denied a
    "connect as sender" request
    """
    pass

class DenyReceiverException(Exception):
    """
    Exception for HoneynetReceiverClient when server denied a
    "connect as receiver" request
    """
    pass

class UnknownRequestException(Exception):
    """
    Exception for HoneynetSocketServer when the server received
    an unknown request
    """
    pass

class UnknownResponseException(Exception):
    """
    Exception for HoneynetSocketClient when the client received
    an unknown response
    """
    pass