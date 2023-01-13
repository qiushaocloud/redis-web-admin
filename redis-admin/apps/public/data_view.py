# coding=utf-8
"""
数据相关视图
"""

try:
    from html import escape  # python 3.x
except ImportError:
    from cgi import escape  # python 2.x
from public.redis_api import get_redis_info
from conf import logs


def get_value(fullkey, db, client):
    cl = client
    m_type = cl.type(fullkey)
    m_ttl = cl.ttl(fullkey)
    m_ttl = m_ttl and m_ttl or ''
    redis_version = get_redis_info(cl=cl, number=1)['redis_version']
    if redis_version >= '2.2.3':
        try:
            m_encoding = cl.object('encoding', fullkey)
        except Exception as e:
            logs.debug('cli error:{0}, {1}'.format(client, e))
            m_encoding = ''
    else:
        m_encoding = ''
    m_len = 0
    val = []

    if m_type == 'string':
        val = cl.get(fullkey)
        m_len = len(val)

    if m_type == 'hash':
        m_len = cl.hlen(fullkey)
        val = client.hgetall(fullkey)
    elif m_type == 'list':
        m_len = cl.llen(fullkey)
        val = client.lrange(fullkey, 0, -1)
    elif m_type == 'set':
        m_len = len(cl.smembers(fullkey))
        m_detail = client.smembers(fullkey)

        val = []
        for va in m_detail:
            val.append(va)
    elif m_type == 'zset':
        m_len = len(cl.zrange(fullkey, 0, -1))
        values = client.zrange(fullkey, 0, -1)
        val = []
        for value in values:
            score = client.zscore(fullkey, value)
            vals = {score: value}
            val.append(vals)

    out = {'type': m_type, 'ttl': m_ttl, 'db': db, 'encoding': m_encoding, 'size': m_len, 'value': val,
           'key': fullkey}
    return out
