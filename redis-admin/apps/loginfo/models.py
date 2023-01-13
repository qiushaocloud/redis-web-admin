# coding:utf-8
from __future__ import unicode_literals

from django.db import models
from datetime import datetime


# Create your models here.


class OperationInfo(models.Model):
    username = models.CharField(max_length=20, verbose_name=u"用户名")
    server = models.CharField(max_length=20, verbose_name=u"redis名")
    db = models.CharField(max_length=20, verbose_name=u"db名")
    key = models.CharField(max_length=120, db_index=True, verbose_name=u"key")
    old_value = models.TextField(verbose_name=u"old value")
    value = models.TextField(null=True, blank=True, verbose_name=u"value")
    type = models.CharField(max_length=10, verbose_name=u"类型")
    add_time = models.DateTimeField(default=datetime.now, verbose_name=u"添加时间")

    class Meta:
        verbose_name = u"操作记录"
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.key
