__author__ = 'yuanchun'

class UnableToConnectException(Exception):
    pass

class DenySenderException(Exception):
    pass

class DenyReceiverException(Exception):
    pass

class UnknownRequestException(Exception):
    pass

class UnknownResponseException(Exception):
    pass