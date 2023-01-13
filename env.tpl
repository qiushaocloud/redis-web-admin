### application-dev.yml 环境变量
RM_AUTH_USERNAME = admin #管理员用户名
RM_AUTH_PASSWORD = password #管理员密码
RM_AHTH_EMAIL = rmadmintest@126.com #管理员邮箱
DATASOURCE_HOST = localhost # mysql 连接 host
DATASOURCE_PORT = 3306 # mysql 连接 port
DATASOURCE_USERNAME = root # mysql 用户名
DATASOURCE_PASSWORD = rootmysqlpassword # mysql 密码
TEST_REDIS_PASSWORD = redispassword # 测试 redis 密码
MAIL_HOST = smtp.126.com # 发送邮箱的host
MAIL_USER = sendmailtest@126.com # 发送邮箱的账号
MAIL_PASS = "sendmailpass" # 发送邮箱账号的密码
ADMIN_MAIL_RECEIVERS = ["recvmailtest01@126.com", "recvmailtest01@126.com"] # 接收邮件的管理人员邮箱

### docker-compose 用到的变量
EXTERNAL_SVC_PORT = 9898 #对外提供服务端口
EXTERNAL_MYSQL_PORT = 9897 #对外 mysql 端口
EXTERNAL_TEST_REDIS_PORT = 9896 #对外 test redis 端口

### volumes dir
MYSQL_DIR = /srv/docker-volumes/redis-web-admin/mysql #mysql 存储目录映射
TEST_REDIS_DIR = /srv/docker-volumes/redis-web-admin/test-redis #test-redis 存储目录映射