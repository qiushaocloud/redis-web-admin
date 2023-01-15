#!/bin/bash
echo "run init_application_dev_yml.sh"

if [ ! -f "application.yml" ]; then
    echo "application.yml not exist"
    cp application.yml.tpl application.yml
    sed -i "s/<SERVER_PORT>/$SERVER_PORT/" application.yml
fi

cd /app/redis-admin/src/main/java/com/xianxin/redis/admin/framework/handler
if [ ! -f "InitSysUserHandler.java" ]; then
    echo "InitSysUserHandler.java not exist"
    cp InitSysUserHandler.java.tpl InitSysUserHandler.java
    sed -i "s/<RM_AUTH_USERNAME>/$RM_AUTH_USERNAME/" InitSysUserHandler.java
    sed -i "s/<RM_AUTH_PASSWORD>/$RM_AUTH_PASSWORD/" InitSysUserHandler.java
fi