#!/bin/bash
echo "run init_application_dev_yml.sh"

if [ ! -f "application.yml" ]; then
    echo "application.yml not exist"
    cp application.yml.tpl application.yml
    sed -i "s/<SERVER_PORT>/$SERVER_PORT/" application.yml
fi