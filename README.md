# redis-web-admin

### 介绍
轻量级Redis缓存图形化管理工具，包含redis的5种数据类型的CRUD操作，基于 xianxin98/redis-admin(https://gitee.com/xianxin98/redis-admin) 二次封装

### docker-compose 运行

1.  执行命令授予执行脚本权限：`sed -i -e 's/\r$//' *.sh && chmod -R 755 *.sh`
2.  执行 `copy env.tpl .env`，并且配置 .env
3.  运行 ./run-docker.sh 【注：docker-compose 低版本识别不了 .env，需要进行升级，作者用的版本是: 1.29.2】
4.  查看日志: docker logs qiushao-redis-web-manager
5.  默认密码如下:
```
    web admin >
        username: admin
        password: rmpassword

    test redis >
        password: redispassword
```

### 测试地址：https://www.qiushaocloud.top/redis-web-admin/
> 温馨提示：请您测试的时候不要修改这两个账号的密码哦，您可以用 admin 账号创建一个属于您的 admin/普通 账号进行测试
* 超级管理员：账号不提供 密码: 不提供
* 普通成员：test(账号) test(密码)

### 下载镜像
```
docker pull qiushaocloud/redis-web-admin
```

### 软件架构【原作者】
 **后端【原作者】** 
1. springboot 2.2.2.RELEASE  :leaves: 
2. JDK 1.8
3. jedis 3.2.0
4. commons-lang3 3.5
5. hutool-core 5.1.1  友情链接：[Java工具集-糊涂官网](https://www.hutool.cn/)
6. fastjson 1.2.62
7. h2database 1.4.200


 **前端【原作者】** 
1. vue-admin 1.0.5 参考作者`taylorchen709`的案例，项目地址：[vue-admin](https://github.com/taylorchen709/vue-admin)
2. axios 0.15.3
3. element-ui 2.13.0 [观望饿了吗](https://element.eleme.cn/) :sunglasses: 
4. font-awesome 4.7.0
5. nprogress 0.2.0
6. vue 2.2.2 [观望VUE](https://cn.vuejs.org/v2/api/) :leaves: 
7. vue-router 2.3.0
8. vuex 2.0.0-rc.6

#### 调整内容【原作者】
- 用户管理支持多用户、用户的增删改
- redis配置存储到h2数据库
- 前端页面增加了简单的权限控制
- 支持同一ip的不同端口

### 后期计划【原作者】
- 考虑增加查询命中率、集群、哨兵模式等，有兴趣的同学可以加入组织探讨

#### 使用说明【原作者】

![登录](https://images.gitee.com/uploads/images/2020/0129/210212_b2ecf9f2_1571481.png "1.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210229_57df8d0d_1571481.png "2.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210246_fb05c272_1571481.png "3.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210259_f3e147f7_1571481.png "4.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210347_d1bf5223_1571481.png "5.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210403_5defd7c0_1571481.png "6.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210413_5c3710a8_1571481.png "7.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210447_c9219ea0_1571481.png "8.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210523_655bdf3f_1571481.png "9.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210534_ec91c006_1571481.png "10.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210628_50749f0f_1571481.png "11.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210638_66d4a23c_1571481.png "12.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210647_ed664a59_1571481.png "13.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210657_252ab391_1571481.png "14.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210707_3bd613a8_1571481.png "15.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210716_01dd56cd_1571481.png "16.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210725_4617115e_1571481.png "17.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210735_04c28274_1571481.png "18.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210748_5e7cb0cc_1571481.png "19.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210759_b34f098f_1571481.png "20.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0129/210101_51e16f47_1571481.png "21.png")


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
