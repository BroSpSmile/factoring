#!/bin/sh
pid=`jps|grep start|awk -F' '  '{print $1}'`
echo $pid
kill -9 $pid