### application-dev.yml 环境变量
SERVER_PORT = 80 #容器内服务端口
RM_AUTH_USERNAME = admin #管理员用户名
RM_AUTH_PASSWORD = password #管理员密码

### docker-compose 用到的变量
EXTERNAL_SVC_PORT = 9898 #对外提供服务端口

### volumes dir
RWA_DATA_DIR = /srv/docker-volumes/redis-web-admin/data #数据存储目录映射