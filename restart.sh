#!/bin/bash
pid=`jps|grep start|awk -F' '  '{print $1}'`
echo $pid
kill -9 $pid
echo "kill $pid success"
nohup java -jar target/start-0.0.1-SNAPSHOT.jar>out.log&
echo "restart success"