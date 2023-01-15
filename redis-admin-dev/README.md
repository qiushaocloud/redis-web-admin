# redis-admin

#### 介绍
轻量级Redis缓存图形化管理工具，包含redis的5种数据类型的CRUD操作

#### 软件架构
 **后端** 
1. springboot 2.2.2.RELEASE  :leaves: 
2. JDK 1.8
3. jedis 3.2.0
4. commons-lang3 3.5
5. hutool-core 5.1.1  友情链接：[Java工具集-糊涂官网](https://www.hutool.cn/)
6. fastjson 1.2.62
7. h2database 1.4.200


 **前端** 
1. vue-admin 1.0.5 参考作者`taylorchen709`的案例，项目地址：[vue-admin](https://github.com/taylorchen709/vue-admin)
2. axios 0.15.3
3. element-ui 2.13.0 [观望饿了吗](https://element.eleme.cn/) :sunglasses: 
4. font-awesome 4.7.0
5. nprogress 0.2.0
6. vue 2.2.2 [观望VUE](https://cn.vuejs.org/v2/api/) :leaves: 
7. vue-router 2.3.0
8. vuex 2.0.0-rc.6


#### 安装教程

1. 安装JDK1.8以上(Java同学已安装的请忽略) 友情推荐：[安装JDK教程](https://jingyan.baidu.com/article/c74d60003588974f6a595db6.html)
2. 打开`./bin/`目录 执行启动服务脚本 `startup.bat` 或 `startup.sh`
3. 打开`./bin/`目录 执行停止服务脚本 `shutdown.bat` 或 `shutdown.sh`
4. 项目启动成功后，在浏览器中输入：[http://localhost:9898/dist/index.html#/login](http://localhost:8081/dist/index.html#/login)
5. 系统默认用户名: admin 密码: admin
6. 已发布到 https://hub.docker.com 搜索 `aoyanfei/redis-admin` 【推荐使用docker】

#### 下载镜像
```
docker pull aoyanfei/redis-admin
```

#### 调整内容
- 用户管理支持多用户、用户的增删改
- redis配置存储到h2数据库
- 前端页面增加了简单的权限控制
- 支持同一ip的不同端口

### 后期计划
- 考虑增加查询命中率、集群、哨兵模式等，有兴趣的同学可以加入组织探讨

#### 使用说明

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


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)