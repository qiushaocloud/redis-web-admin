#!/bin/bash
echo "run init_application_dev_yml.sh"

if [ ! -f "application-dev.yml" ]; then
    echo "application-dev.yml not exist"
    cp application-dev.yml.tpl application-dev.yml
    sed -i "s/<SERVER_PORT>/$SERVER_PORT/" application-dev.yml
    sed -i "s/<DATASOURCE_ADDR>/$DATASOURCE_ADDR/" application-dev.yml
    sed -i "s/<DATASOURCE_USERNAME>/$DATASOURCE_USERNAME/" application-dev.yml
    sed -i "s/<DATASOURCE_PASSWORD>/$DATASOURCE_PASSWORD/" application-dev.yml
fi