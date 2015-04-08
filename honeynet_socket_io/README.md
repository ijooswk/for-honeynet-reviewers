# Honeynet Socket IO
Task Description: A program that read inputs from one socket, and write it to another.

# Usage
To start server:
```shell
python HoneynetSocketServer.py -H <host> -p <port>
```

To start sender client:
```shell
python HoneynetSocketClient.py -H <host> -p <port> -t sender
```
Then type anything.

To start receiver client:
```shell
python HoneynetSocketClient.py -H <host> -p <port> -t receiver
```
Anything typed by sender will be displayed.

You can start multiple senders and receivers.

To run unit tests:
```shell
python TestHoneynetSocketServer.py
python TestHoneynetSocketClient.py
```
