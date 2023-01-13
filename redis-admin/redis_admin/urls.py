"""dct_redis URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from users.views import LoginViews, LogoutView
from loginfo.views import OperationInfoEditView, OperationInfoDelView, UserManageView
from users.views import ChangeUser, AddUser, UserRegisterView, EditUser
from monitor.views import (GetKeyView, GetRedisInfo, CheckRedisContent, GetIdView,
                           GetValueView, RedisErrorHtmlView, ClientHtmlView, ClientListView,
                           DelKeyView, EditValueTableView, BgSaveView, AddKeyView, ClearDbView,
                           RedisListView, RedisEditView, RedisAddView, RedisDelView
                           )

urlpatterns = [
    url(r'^$', GetRedisInfo.as_view(), name='index'),
    url(r'^check/redis/$', CheckRedisContent.as_view(), name='checkredis'),
    url(r'^error/$', RedisErrorHtmlView.as_view(), name='redis_error'),
    url(r'^login/$', LoginViews.as_view(), name='login'),
    url(r'^logout/$', LogoutView.as_view(), name='logout'),
    url(r'^(?P<redis_name>\w+)/db(?P<id>[0-9]+)/$', GetIdView.as_view(), name='getid'),
    url(r'^get_key/(?P<redis_name>\w+)/(?P<db_id>[0-9]+)/$', GetKeyView.as_view(), name='getkey'),
    url(r'^view/(?P<redis_name>\w+)/(?P<value_db_id>[0-9]+)/(?P<key>.*)/$', GetValueView.as_view(),
        name='getvalue'),
    url(r'^client/(?P<client_id>[0-9]+)/$', ClientHtmlView.as_view(), name='client_html'),
    url(r'^client_list/$', ClientListView.as_view(), name='client_list'),
    url(r'^del/key/$', DelKeyView.as_view(), name='del_key'),
    url(r'^edit/(?P<redis_name>\w+)/db(?P<edit_db_id>[0-9]+)/$', EditValueTableView.as_view(),
        name='edit_key'),
    url(r'^bgsave/(?P<redis_id>[0-9]+)/$', BgSaveView.as_view(), name='bg_save'),
    url(r'^operation/info/edit/$', OperationInfoEditView.as_view(), name='operation_edit'),
    url(r'^operation/info/del/$', OperationInfoDelView.as_view(), name='operation_del'),
    url(r'^user/manage/$', UserManageView.as_view(), name='user_manage'),
    url(r'^change/user/$', ChangeUser.as_view(), name='change_user'),
    url(r'^add/user/$', AddUser.as_view(), name='add_user'),
    url(r'^edit/user/$', EditUser.as_view(), name='edit_user'),
    url(r'^add/key/(?P<redis_name>\w+)/$', AddKeyView.as_view(), name='add_key'),
    url(r'^clear/db$', ClearDbView.as_view(), name='clear_db'),
    url(r'^register/$', UserRegisterView.as_view(), name="register"),
    url(r'^redis/list/$', RedisListView.as_view(), name="redis_list"),
    url(r'^redis/list/edit/$', RedisEditView.as_view(), name="redis_edit"),
    url(r'^redis/list/add/$', RedisAddView.as_view(), name="redis_add"),
    url(r'^redis/list/del/$', RedisDelView.as_view(), name="redis_del"),
]
