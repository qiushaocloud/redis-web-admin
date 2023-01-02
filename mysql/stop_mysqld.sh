#!/bin/bash
echo "start stop mysqld"
pids=`ps aux|grep "mysqld"|grep -v grep|awk '{print $2}'`
for pid in $pids
do
        echo killpid:$pid
        kill -9 $pid
done
echo "fins stop mysqld"