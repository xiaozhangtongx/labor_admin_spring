## Labor Admin 后台

> 由于暂时没有上微服务，所以现在的就只有一个主启动类`` LaborAdmin9001``

### 1.所用技术
- spring boot
- mybatis plus
- Spring Security

### 2.项目环境

```xml
<java.version>1.8<java.version>
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
<junit.version>4.13.1</junit.version>
<log4j.version>1.2.14</log4j.version>
<lombok.version>1.16.18</lombok.version>
<mysql.version>8.0.28</mysql.version>
<druid.version>1.1.16</druid.version>
<springfox.version>3.0.0</springfox.version>
<hutool.version>5.3.3</hutool.version>
<mybatis.plus.spring.boot.version>3.1.0</mybatis.plus.spring.boot.version>
```

### 3. 项目运行

#### 3.1 下载项目

```bash
git clone https://github.com/xiaozhangtongx/labor_admin_spring.git
```

#### 3.2 安装maven依赖

安装`pom.xml`里面的依赖

#### 3.3 配置数据库

在`application.yml`修改数据库的配置

```yaml
spring:
  application:
    name: labor-admin-server
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/数据库名称?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: 账号
    password: 密码
```

#### 3.4 配置端口和api

```yaml
#配置端口
server:
  port: 端口
  servlet:
    context-path: /api/v1
```

#### 3.5 运行项目

运行` LaborAdmin9001`即可

#### 3.6 查看swagger和druid

- swagger 

  地址：http://localhost:9001/api/v1/swagger-ui/

  注意：有些接口需要token，所以需要在`header`中配置``Authorization``

- druid

  在`DruidConfig`中配置用户名和密码,我的分别为`admin 123456`

  地址：http://localhost:9001/api/v1/druid/index.html