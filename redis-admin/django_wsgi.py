#!/usr/bin/env python
# coding:utf-8
__author__ = 'carey'
__date__ = '2017/10/13'

import os
os.environ['DJANGO_SETTINGS_MODULE'] = 'redis_admin.settings'
import django.core.handlers.wsgi

application = django.core.handlers.wsgi.WSGIHandler()