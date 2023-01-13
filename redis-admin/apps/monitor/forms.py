# _*_ encode:utf-8 _*_

from django import forms
from users.models import RedisConf


class RedisForm(forms.ModelForm):
    class Meta:
        model = RedisConf
        fields = '__all__'
