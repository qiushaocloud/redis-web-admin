# encoding:utf-8
from __future__ import unicode_literals

from django.db import models
# from users.models import PermissionModel
#
# # Create your models here.
#
#
# class RedisConfigModel(models.Model):
#     index = models.IntegerField(verbose_name=u"索引")
#     name = models.CharField(max_length=10, verbose_name=u"名称")
#     host = models.IPAddressField(verbose_name=u"IP地址")
#     port = models.IntegerField(verbose_name=u"端口")
#     password = models.CharField(max_length=30, verbose_name=u"密码")
#     database = models.IntegerField(verbose_name=u"db数")
#     permission = models.ManyToManyField(PermissionModel)
#
#     class Meta:
#         verbose_name = "redis配置"
#         verbose_name_plural = verbose_name
#         db_table = 'redis_config'
#
#     def __str__(self):
#         return self.name