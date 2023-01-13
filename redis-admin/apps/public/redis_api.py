# coding=utf-8
import redis
import socket
import sys

from conf import logs
from conf.conf import base, socket_timeout,  scan_batch
from redis.exceptions import (
    RedisError,
    ConnectionError,
    TimeoutError,
    BusyLoadingError,
    ResponseError,
    InvalidResponse,
    AuthenticationError,
    NoScriptError,
    ExecAbortError,
    ReadOnlyError
)
from rediscluster import StrictRedisCluster
from django.db.models import Q
from django.core.exceptions import MultipleObjectsReturned
from collections import Iterable
from redis._compat import nativestr
from users.models import RedisConf
from monitor.forms import RedisForm

client = None
server_ip = None
db_index = None


def cluster_connect(conf, password=None):
    logs.debug('redis cluster connect:{0},password:{1}'.format(conf, password))
    return StrictRedisCluster(startup_nodes=conf, decode_responses=True, socket_timeout=socket_timeout)


def get_cluster_first_conf(name):
    redis_obj = RedisConf.objects.filter(name=name)[1]
    return redis_obj


def get_all_cluster_redis():
    clusters = RedisConf.objects.filter(type=1)
    cluster_name_list = []
    cluster_list = []
    for cluster in clusters:
        if cluster.name not in cluster_name_list:
            cluster_name_list.append(cluster.name)
            cluster_list.append(cluster)
    return cluster_list


def get_redis_conf(name=None, user=None):
    if name is None and user is not None:
        return user.auths.all()
    else:
        try:
            return RedisConf.objects.get(name=name)
        except MultipleObjectsReturned:
            cluster_confs = RedisConf.objects.filter(name=name)
            cluster_conf_list = list()
            for cluster in cluster_confs:
                cluster_conf_dict = dict()
                cluster_conf_dict['id'] = cluster.id
                cluster_conf_dict['host'] = cluster.host
                cluster_conf_dict['port'] = cluster.port
                cluster_conf_dict['password'] = cluster.password
                cluster_conf_dict['name'] = cluster.name
                cluster_conf_list.append(cluster_conf_dict)
                del cluster_conf_dict
            return cluster_conf_list
        except Exception as e:
            logs.error(e)
    return False


def connect(*args, **kwargs):
    """ 连接Redis数据库，参数和redis-py的Redis类一样 """
    global client
    client = redis.Redis(*args, **kwargs)


def get_client(*args, **kwargs):
    global server_ip
    global db_index
    if args or kwargs:
        if server_ip is not None and db_index is not None:
            if kwargs['host'] == server_ip and kwargs['db'] == db_index:
                pass
            else:
                connect(*args, **kwargs)
                server_ip = kwargs['host']
                db_index = kwargs['db']
        else:
            connect(*args, **kwargs)
            server_ip = kwargs['host']
            db_index = kwargs['db']
            
    global client
    if client:
        return client
    else:
        connect(host='127.0.0.1', port=6379)
        return client


def get_tmp_client(*args, **kwargs):
    from redis import Redis
    return Redis(*args, **kwargs)


def get_all_keys_dict(client=None):
    if client:
        m_all = client.keys()
    else:
        m_all = get_client().keys()
    m_all.sort()
    me = {}
    for key in m_all:
        if len(key)>100:
            continue
        key_levels = key.split(base['seperator'])
        cur = me
        for lev in key_levels:
            if cur.has_key(lev):
                if cur.keys()==0:
                    cur[lev] = {lev:{}}#append(lev)
            else:
                cur[lev] = {}
            cur = cur[lev]
    return me


def get_all_keys_tree(client=None, key='*', cursor=0, min_num=None, max_num=None):
    client = client or get_client()
    key = key or '*'
    if key == '*':
        data = client.scan(cursor=cursor, match=key, count=scan_batch)
    else:
        key = '*%s*' % key
        data = client.scan(cursor=cursor, match=key, count=scan_batch)
    if isinstance(data, tuple):
        data = data[1]
    elif isinstance(data, dict):
        data_list = list()
        for k, v in data.items():
            data_list.extend(v[1])
        data = data_list
    data = data[min_num:max_num]
    data.sort()
    return data


def check_connect(host, port, password=None, socket_timeout=socket_timeout):
    # from redis import Connection
    try:
        conn = Connection(host=host, port=port, password=password, socket_timeout=socket_timeout)
        conn.connect()
        return True
    except Exception as e:
        logs.error(e)
        return e


def check_redis_connect(name):
    redis_conf = get_redis_conf(name)
    if isinstance(redis_conf, list):
        try:
            conn = cluster_connect(conf=redis_conf)
            logs.debug('check redis connect: {0}'.format(redis_conf))
            conn.ping()
            return True
        except Exception as e:
            logs.error(e)
            error = dict(
                redis=name,
                message=e,
            )
            return error
    else:
        try:
            logs.debug("host:{0},port:{1},password:{2},timeout:{3}".format(
                redis_conf.host, redis_conf.port, redis_conf.password, socket_timeout))
            conn = Connection(host=redis_conf.host, port=redis_conf.port,
                              password=redis_conf.password, socket_timeout=socket_timeout)
            conn.connect()
            return True
        except Exception as e:
            logs.error(e)
            error = dict(
                redis=name,
                message=e,
            )
            return error


def get_cl(redis_name, db_id=0):
    cur_db_index = int(db_id)
    server = get_redis_conf(name=redis_name)
    if server is not False:
        if isinstance(server, list):
            conn = cluster_connect(conf=server)
            return conn
        else:
            if server.password is None:
                cl = get_client(host=server.host, port=server.port, db=cur_db_index, password=None)
            else:
                cl = get_client(host=server.host, port=server.port, db=cur_db_index, password=server.password)
            return cl
    else:
        return False


class Connection(redis.Connection):
    """
    继承redis Connection
    """
    def connect(self):
        "Connects to the Redis server if not already connected"
        if self._sock:
            return
        try:
            sock = self._connect()
        except socket.error:
            e = sys.exc_info()[1]
            raise ConnectionError(self._error_message(e))

        self._sock = sock
        try:
            self.on_connect()
        except RedisError:
            # clean up after any error in on_connect
            self.disconnect()
            raise

        # run any user callbacks. right now the only internal callback
        # is for pubsub channel/pattern resubscription
        for callback in self._connect_callbacks:
            callback(self)

    def on_connect(self):
        "Initialize the connection, authenticate and select a database"
        self._parser.on_connect(self)

        # if a password is specified, authenticate
        if self.password:
            self.send_command('AUTH', self.password)
            if nativestr(self.read_response()) != 'OK':
                logs.error("Invalid Password")
                raise AuthenticationError('Invalid Password')

        # if a database is specified, switch to it
        if self.db >= 0: # 密码为空，切换db判断是否需要认证
            self.send_command('SELECT', self.db)
            if nativestr(self.read_response()) != 'OK':
                raise ConnectionError('Invalid Database')


def redis_conf_save(request):
    redis_id = request.POST.get("id", None)

    # 编辑
    if redis_id is not None:
        try:
            redis_obj = RedisConf.objects.get(id=redis_id)
            redis_form = RedisForm(request.POST, instance=redis_obj)
            if redis_form.is_valid():
                redis_form.save()
                return True
            return False
        except Exception as e:
            logs.error(e)
            return False

    # 添加
    else:
        redis_form = RedisForm(request.POST)
        if not redis_form.is_valid():
            logs.error(redis_form.errors)
            return False

        name = request.POST.get('name', None)
        redis_type = request.POST.get('type', None)
        # 单机redis添加
        if redis_type is None or int(redis_type) == 0:
            if RedisConf.objects.filter(name__iexact=name).count() == 0:
                redis_form.save()
                return True
            else:
                logs.error('单机redis添加错误: 名称重复, data:{0}'.format(request.POST))
                return False

        # cluster添加
        elif int(redis_type) == 1:
            if RedisConf.objects.filter(Q(type=0) & Q(name__iexact=name)).count() == 0:
                redis_form.save()
                return True
            else:
                logs.error('cluster redis添加错误: 名称与单机重复, data:{0}'.format(request.POST))
                return False
        return False


def get_redis_info(cl, number=0):
    """
    获取redis info信息
    :param cl: connect
    :param number: cluster类型需传递,number=0为获取群集所有redis info, number=1为返回第一个redis info
    :return: info
    """
    data = cl.info()
    if 'redis_version' not in data:
        if number == 1:
            for k, v in data.items():
                data = v
                break
    return data
