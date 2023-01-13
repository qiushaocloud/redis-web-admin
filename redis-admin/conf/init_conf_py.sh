#!/bin/bash
echo "run init_conf_py.sh"

cp conf.py.tpl conf.py

sed -i "s/<MAIL_HOST>/$MAIL_HOST/g" conf.py
sed -i "s/<MAIL_USER>/$MAIL_USER/g" conf.py
sed -i "s/<MAIL_PASS>/$MAIL_PASS/g" conf.py
sed -i "s/<ADMIN_MAIL_RECEIVERS>/$ADMIN_MAIL_RECEIVERS/g" conf.py
sed -i "s/<DATASOURCE_HOST>/$DATASOURCE_HOST/g" conf.py
sed -i "s/<DATASOURCE_USERNAME>/$DATASOURCE_USERNAME/g" conf.py
sed -i "s/<DATASOURCE_PASSWORD>/$DATASOURCE_PASSWORD/g" conf.py
sed -i "s/<DATASOURCE_PORT>/$DATASOURCE_PORT/g" conf.py




