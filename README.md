# redis-web-admin

### 介绍
轻量级Redis缓存图形化管理工具，基于 xianxin98/redis-admin(https://gitee.com/xianxin98/redis-admin) 二次封装

### docker-compose 运行

1.  执行命令授予执行脚本权限：`sed -i -e 's/\r$//' *.sh && chmod -R 755 *.sh`
2.  执行 `copy env.tpl .env`，并且配置 .env
3.  运行 ./run-docker.sh 【注：docker-compose 低版本识别不了 .env，需要进行升级，作者用的版本是: 1.29.2】
4.  查看日志: docker logs qiushao-redis-web-manager


### 软件架构 【原作者】

**后端**

1. springboot 2.2.2.RELEASE ![:leaves:](https://cn-assets.gitee.com/assets/emoji/leaves-b940b10a257c9dd00c6bb025d81f3faf.png)
2. JDK 1.8
3. jedis 3.2.0
4. commons-lang3 3.5
5. hutool-core 5.1.1 友情链接：[Java工具集-糊涂官网](https://gitee.com/link?target=https%3A%2F%2Fwww.hutool.cn%2F)
6. fastjson 1.2.62
7. h2database 1.4.200

**前端**

1. vue-admin 1.0.5 参考作者`taylorchen709`的案例，项目地址：[vue-admin](https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2Ftaylorchen709%2Fvue-admin)
2. axios 0.15.3
3. element-ui 2.13.0 [观望饿了吗](https://gitee.com/link?target=https%3A%2F%2Felement.eleme.cn%2F) ![:sunglasses:](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/sunglasses-f58258a6aad83867089c5d93e550f767_h2mOAq.png)
4. font-awesome 4.7.0
5. nprogress 0.2.0
6. vue 2.2.2 [观望VUE](https://gitee.com/link?target=https%3A%2F%2Fcn.vuejs.org%2Fv2%2Fapi%2F) ![:leaves:](https://cn-assets.gitee.com/assets/emoji/leaves-b940b10a257c9dd00c6bb025d81f3faf.png)
7. vue-router 2.3.0
8. vuex 2.0.0-rc.6

### 安装教程【原作者】

1. 安装JDK1.8以上(Java同学已安装的请忽略) 友情推荐：[安装JDK教程](https://gitee.com/link?target=https%3A%2F%2Fjingyan.baidu.com%2Farticle%2Fc74d60003588974f6a595db6.html)
2. 打开`./bin/`目录 执行启动服务脚本 `startup.bat` 或 `startup.sh`
3. 打开`./bin/`目录 执行停止服务脚本 `shutdown.bat` 或 `shutdown.sh`
4. 项目启动成功后，在浏览器中输入：[http://localhost:9898/dist/index.html#/login](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A8081%2Fdist%2Findex.html%23%2Flogin)
5. 系统默认用户名: admin 密码: admin
6. 已发布到 [https://hub.docker.com](https://gitee.com/link?target=https%3A%2F%2Fhub.docker.com) 搜索 `aoyanfei/redis-admin` 【推荐使用docker】

### 下载镜像【原作者】

```
docker pull aoyanfei/redis-admin
```

### 调整内容【原作者】

- 用户管理支持多用户、用户的增删改
- redis配置存储到h2数据库
- 前端页面增加了简单的权限控制
- 支持同一ip的不同端口

### 后期计划【原作者】

- 考虑增加查询命中率、集群、哨兵模式等，有兴趣的同学可以加入组织探讨

### 使用说明【原作者】

![登录](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210212_b2ecf9f2_1571481_WJurme.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210229_57df8d0d_1571481_H4molY.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210246_fb05c272_1571481.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210259_f3e147f7_1571481_deI4Et.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210347_d1bf5223_1571481.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210403_5defd7c0_1571481.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210413_5c3710a8_1571481_YvG6qv.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210447_c9219ea0_1571481_5FgFvj.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210523_655bdf3f_1571481_Wh6Cjo.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210534_ec91c006_1571481_dbmt5Q.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210628_50749f0f_1571481_4OuuIq.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210638_66d4a23c_1571481.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210647_ed664a59_1571481.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210657_252ab391_1571481_mKjGL7.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210707_3bd613a8_1571481_7mlll4.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210716_01dd56cd_1571481_YKDdEa.png)

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210725_4617115e_1571481.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210735_04c28274_1571481_0Sam4u.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210748_5e7cb0cc_1571481_HJPv90.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210759_b34f098f_1571481_cqPzpa.png)

![输入图片说明](https://githubcdn.qiushaocloud.top/gh/qiushaocloud-cdn/cdn_static@master/uPic/2023-01-02/15-24/210101_51e16f47_1571481_TqEFQz.png)


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
* 此项目是基于 [xianxin98/redis-admin](https://gitee.com/xianxin98/redis-admin) 二次修改
* 以上内容大部分为原作者原创内容
* 如果大家喜欢，请支持 [邱少羽梦(修改者)](https://www.qiushaocloud.top)，也请支持下原作者哦
* 版权归原作者所有，修改者只是进行部分修改，以满足修改者需求
