This project is a scaffold for rapid project construction, integrating database, Redis, email, WeChat official account, es, rabbitmq, mongodb

## 修改功能

### 依赖改动

* ORM框架 使用 Mybatis-Plus 简化CRUD (不支持主子表)
* Bean简化 使用 Lombok 简化 get set toString 等等
* 容器改动 Tomcat 改为 并发性能更好的 undertow
* 分页移除 pagehelper 改为 Mybatis-Plus 分页
* 升级 swagger 为 knife4j
* 集成 Hutool 5.X 并重写部分功能
* 集成 Feign 接口化管理 Http 请求(如三方请求 支付,短信,推送等)
* 移除 自带服务监控 改为 spring-boot-admin 全方位监控
* 增加 demo 模块示例(给不会增加模块的小伙伴做参考)
* 增加 redisson 高性能 Redis 客户端
* 移除 fastjson 统一使用 jackson 序列化
* 集成 dynamic-datasource 多数据源(默认支持MySQL,其他种类需自行适配)
* 集成 Lock4j 实现分布式 注解锁、工具锁 多种多样

### 代码改动

* 所有原生功能使用 Mybatis-Plus 与 Lombok 重写
* 增加 IServicePlus 与 BaseMapperPlus 可自定义通用方法
* 代码生成模板 改为适配 Mybatis-Plus 的代码
* 代码生成模板 拆分出 Vo,QueryBo,AddBo,EditBo 等领域对象
* 代码生成模板 增加 文档注解 与 校验注解 简化通用操作
* 项目修改为 maven多环境配置
* 项目配置修改为 application.yml 统一管理
* 数据权限修改为 适配支持单表、多表
* 使用 redisson 实现 spring-cache 整合
* 增加 mybatis-plus 二级缓存 redis 存储
  
## 内置功能
1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10.  登录日志：系统登录日志记录查询包含登录异常。
11.  在线用户：当前系统中活跃用户状态监控。
12.  定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13.  代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14.  系统接口：根据业务代码自动生成相关的api接口文档。
15.  服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16.  缓存监控：对系统的缓存信息查询，命令统计等。
17.  在线构建器：拖动表单元素生成相应的HTML代码。
18.  连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

