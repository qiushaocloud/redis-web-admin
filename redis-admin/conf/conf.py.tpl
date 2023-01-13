#!/usr/bin/env python
# coding:utf-8
__author__ = 'carey'
__date__ = '2017/12/25'

DEBUG = True

LOG_LEVEL = 'INFO'

# redis
base = {
    'seperator': ':',
    'maxkeylen': 100
}
socket_timeout = 2
scan_batch = 10000  # scan 限制获取数据量
show_key_self_count = False

mail_host = '<MAIL_HOST>'
mail_user = '<MAIL_USER>'
mail_pass = '<MAIL_PASS>'
admin_mail_receivers = <ADMIN_MAIL_RECEIVERS>

database = {
    "name": "redis_admin",
    "host": "<DATASOURCE_HOST>",
    "username": "<DATASOURCE_USERNAME>",
    "password": "<DATASOURCE_PASSWORD>",
    "port": "<DATASOURCE_PORT>"
}
