### application-dev.yml 环境变量
SERVER_PORT = 9898 #容器内服务端口
RM_AUTH_USERNAME = admin #管理员用户名
RM_AUTH_PASSWORD = password #管理员密码
TEST_REDIS_PASSWORD = redispassword # 测试 redis 密码

### docker-compose 用到的变量
EXTERNAL_SVC_PORT = 9898 #对外提供服务端口
EXTERNAL_TEST_REDIS_PORT = 9896 #对外 test redis 端口

### volumes dir
RWA_DATA_DIR = /srv/docker-volumes/redis-web-admin/data #数据存储目录映射
MYSQL_DIR = /srv/docker-volumes/redis-web-admin/mysql #mysql 存储目录映射
TEST_REDIS_DIR = /srv/docker-volumes/redis-web-admin/test-redis #test-redis 存储目录映射