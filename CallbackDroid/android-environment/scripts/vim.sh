#!/bin/bash

if [ $# -ne 1 ] ; then
	echo usage vim.sh NotificationManager
	exit
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
num=`cat $DIR/srcf-* | grep -c /$1.java`
if [ $num -gt 1 ] ; then
	cat $DIR/srcf-* | grep /$1.java
elif [ $num -lt 1 ] ; then
	echo none
else
	vim $DIR/../src/`grep -h /$1.java $DIR/srcf-*`
fi
