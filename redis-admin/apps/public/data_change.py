# coding=utf-8

from conf.conf import scan_batch
from public.redis_api import get_cl


class ChangeData(object):
    def __init__(self, redis_name=None, db_id=None):
        self.redis_name = redis_name
        self.db_id = db_id
        self.cl = get_cl(redis_name=self.redis_name, db_id=self.db_id)

    def delete_key(self, key, cursor=None):
        if cursor is not None:
            key = key + '*'
            next_cursor, m_all = self.cl.scan(cursor=cursor, match=key, count=scan_batch)
            for e in m_all:
                self.cl.delete(e)
            return True
        else:
            if self.cl.delete(key):
                return True
            else:
                return False

    def delete_value(self, key, value):
        type = self.cl.type(key)
        if type == "hash":
            self.cl.hdel(key, value)
        elif type == "list":
            self.cl.lset(key, value, '__delete__')
            self.cl.lrem(key, '__delete__', 0)
        elif type == "set":
            self.cl.srem(key, value)
        elif type == "zset":
            self.cl.zrem(key, value)

    def rename_key(self, key, new):
        self.cl.rename(key, new)

    def edit_value(self, key, value, new, score):
        type = self.cl.type(key)
        if type == "hash":
            self.cl.hset(key, value, new)
        elif type == "list":
            self.cl.lset(key, value, new)
        elif type == "set":
            self.cl.srem(key, value)
            self.cl.sadd(key, new)
        elif type == "zset":
            self.cl.zrem(key, value)
            self.cl.zadd(key, new, float(score))
        elif type == "string":
            self.cl.set(key, new)

    def add_value(self, key, value, name, score):
        type = self.cl.type(key)
        if type == "hash":
            self.cl.hset(key, name, value)
        elif type == "list":
            self.cl.rpush(key, value)
        elif type == "set":
            self.cl.sadd(key, value)
        elif type == "zset":
            self.cl.zadd(key, value, float(score))

    def change_ttl(self, key, new):
        self.cl.expire(key, new)

    def add_key(self, key, value, type, score=None, vkey=None):
        if type == 'string':
            self.cl.set(key, value)
        elif type == 'zset':
            self.cl.zadd(key, value, float(score))
        elif type == 'set':
            self.cl.sadd(key, value)
        elif type == 'hash':
            self.cl.hset(key, vkey, value)
        elif type == 'list':
            self.cl.rpush(key, value)
