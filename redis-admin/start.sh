#!/bin/bash

start() {
    gunicorn -c funicorn.py redis_admin.wsgi
    if [ $? -eq 0 ]; then
        echo 'start [ successful ]'
    else
        echo 'start [ failed ]'
    fi
}

stop() {
    pid=`cat ./log/gunicorn.pid`
    kill ${pid}
    if [ $? -eq 0 ]; then
        echo 'stop [ successful ]'
    else
        echo 'stop [ failed ]'
    fi
}

help() {
    echo "Usage: $0 [ start | stop | restart ]"
    exit 1
}

restart() {
    stop && start
}

case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    help|--help|-h)
        help
        ;;
    *)
        echo "Usage: --help | -h | help"
        exit 1
        ;;
esac
exit 0