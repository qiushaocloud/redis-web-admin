# redis-web-admin

### 介绍
一个简单好用的redis缓存图形化管理工具，包含redis的5种数据类型的CRUD操作; 由于该系统是在大名鼎鼎的JeeSite基础之上开发的，所有保留了原系统的用户/角色/权限/菜单等模块，基于 XueBuSi/redis-admin(https://gitee.com/XueBuSi/redis-admin) 二次封装

### docker-compose 运行

1.  执行命令授予执行脚本权限：`sed -i -e 's/\r$//' *.sh && chmod -R 755 *.sh`
2.  执行 `copy env.tpl .env`，并且配置 .env
3.  运行 ./run-docker.sh 【注：docker-compose 低版本识别不了 .env，需要进行升级，作者用的版本是: 1.29.2】
4.  查看日志: docker logs qiushao-redis-web-manager


### 软件涉及技术 【原作者】

1. SpringBoot
2. SpringMVC
3. Mybatis
4. JSP
5. JQuery
6. Ehcache
7. MySQL
8. Redis

### 安装教程【原作者】

1. 创建名为x-redis-admin数据库
2. 在创建好的数据库中执行 sql/db.sql 脚本文件来初始化基础数据
3. 修改application-dev.yml中的数据库名称、用户名和密码, 以及修改redis配置
4. 运行App.java中的main方法启动系统
5. 默认浏览器访问地址 [http://localhost:18080](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A18080)
6. 默认系统登陆账号 admin 密码 admin

### 打包运行【原作者】

1. 打包:执行命令 mvn clean package 对项目进行打包，打好的war包生成在项目的target目录下.
2. 生产中运行:打好war包之后，在linux下请执行该命令来启动项目: java -jar redis-admin-1.0.0-SNAPSHOT.war

### 有图有真相【原作者】

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/1-1541241037174_XjMEoM.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/2-1541559258468_Lzf1mB.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/3-1541241493839_FowgTw.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/4-1541241149816_PnBKDZ.png)

![img](https://gitee.com/xuebusi/redis-admin/raw/master/screenshot/4-1541241225580.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/5-1541241277696_cOx6bo.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/6-1541241312722_b6Ysll.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/7-1541241360744_NRPzPL.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/8-1541241385881_dc1kTG.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/9-1541241421674_hmwfGp.png)

![img](https://gitee.com/xuebusi/redis-admin/raw/master/screenshot/10-1541241456056.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/11-1542275834144_WtQEtB.png)

![img](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/18-55/12-1542275862427_HUDc6e.png)

### 项目计划【原作者】

1. 实现redis主机和端口的图形化配置功能
2. 实现集合中元素的修改操作
3. 实现Reids集群配置管理功能
4. 实现Redis集群监控

### 参与原作者贡献

欢迎优秀的开发人员参与该项目, 贡献自己的代码, 一起做一个最好的Redis客户端工具! 联系QQ 490983587


### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


### 开源不易，如果对您有帮助，请您动一动您的小手，给作者点 Star，也请您多多关注分享者「[邱少羽梦](https://www.qiushaocloud.top)」

* 分享者邮箱: [qiushaocloud@126.com](mailto:qiushaocloud@126.com)
* [分享者博客](https://www.qiushaocloud.top)
* [分享者自己搭建的 gitlab](https://gitlab.qiushaocloud.top/qiushaocloud) 
* [分享者 gitee](https://gitee.com/qiushaocloud/dashboard/projects) 
* [分享者 github](https://github.com/qiushaocloud?tab=repositories) 


### 版权信息公告:
* 此项目是基于 [XueBuSi/redis-admin](https://gitee.com/XueBuSi/redis-admin) 二次修改
* 以上内容大部分为原作者原创内容
* 如果大家喜欢，请支持 [邱少羽梦(修改者)](https://www.qiushaocloud.top)，也请支持下原作者哦
* 版权归原作者所有，修改者只是进行部分修改，以满足修改者需求
