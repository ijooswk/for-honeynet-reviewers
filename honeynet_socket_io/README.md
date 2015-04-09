# Honeynet Socket IO

## About
This is a coding task of the Honeynet project.

Task Description: A program that read inputs from one socket, and write it to another.

In my implementation, a socket server is responsible for reading inputs from sender clients 
and writing it to receiver clients. The server is threaded, and it support concurrent requests
from multiple senders and receivers.

I implemented two modules to accomplish this task:

1. HoneynetSocketClient. This module include the sender client and receiver client.
2. HoneynetSocketServer. This module include the threaded server which do the redirection.

## Usage
### To start server:
```shell
python HoneynetSocketServer.py -H <host> -p <port>
```

### To start sender client:
```shell
python HoneynetSocketClient.py -H <host> -p <port> -t sender
```
Then type anything.


### To start receiver client:
```shell
python HoneynetSocketClient.py -H <host> -p <port> -t receiver
```
Anything typed by sender will be displayed.
You can start multiple senders and receivers.

### To run unit tests:
```shell
python TestHoneynetSocketServer.py
python TestHoneynetSocketClient.py
```
note that ipv4 address `localhost:12345` is used for testing,
you should make sure this address is not in using during testing.