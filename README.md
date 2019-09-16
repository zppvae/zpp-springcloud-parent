# zpp-springcloud-parent

## 项目介绍
springcloud 集成 Eureka、Zuul、config、oauth2.0

## 服务部署

- 创建`mysql`数据库，默认数据库名为`zpp-springcloud`，可修改数据库名，同时也要修改`zpp-springcloud-config`
中`resources/config`目录下对应的配置文件，执行目录`doc`下的sql文件
- 将`doc/lib`下对应的jar包放到本地仓库对应的目录下（没有会报错）

### 启动顺序

- EurekaApplication（注册中心）
- ConfigApplication（分布式配置）
- GatewayApplication（网关）
- AuthApplication（授权服务）
- UserApplication（用户服务）

## 分布式配置

### 加密
https://github.com/ulisesbocchio/jasypt-spring-boot

```
# input：加密参数
# password：加密秘钥
# algorithm：加密算法
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="xxx" password=aaa algorithm=PBEWithMD5AndDES

输出：
----ENVIRONMENT-----------------

Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 25.60-b23

----ARGUMENTS-------------------

algorithm: PBEWithMD5AndDES
input: xxx
password: aaa

----OUTPUT----------------------
IbmvPODFXzeMDeDeaCSW8255mJA=
```

## 过滤器

### GatewayFilter与GlobalFilter
Global filters 被应用到所有的路由上。

Gateway filter将应用到单个路由上或者一个分组的路由上。

## 授权

`网关URL`：`http://127.0.0.1:8083`

### 通过手机号授权

#### 发送短信验证码

`URL`：`/auth/code/sms?mobile=13000000000`

`请求方式`：`GET`

`请求头`：

| 参数名 | 说明 |
| :------------- | :------------ |
| deviceId     | 唯一Id    |

#### 手机号短信登录
`URL`：`/auth/authentication/mobile`

`请求方式`：`POST`

`请求头`：

| 参数名 | 说明 |
| :------------- | :------------ |
|Content-Type|application/x-www-form-urlencoded|
| deviceId     | 唯一Id    |

`请求参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|mobile    |手机号|
|smsCode   |验证码|

`响应参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|access_token    |访问token|
|token_type   |token类型|
|refresh_token   |刷新access_token的token|
|expires_in   |access_token有效期，单位：s|
|scope   |scope|

`返回示例`：
```
{
    "access_token": "cba39daa-62f6-4334-8a0a-df26ed40a5cc",
    "token_type": "bearer",
    "refresh_token": "6a5d7e0f-bb49-4298-9a15-a5300430c721",
    "expires_in": 1549,
    "scope": "all"
}
```

###  通过用户名密码授权

#### 获取图片验证验证码

`URL`：`auth/code/image`

`请求方式`：`GET`

`请求头`：

| 参数名 | 说明 |
| :------------- | :------------ |
| deviceId     | 唯一Id    |

#### 用户名密码登录
`URL`：`/auth/authentication/form`

`请求方式`：`POST`

`请求头`：

| 参数名 | 说明 |
| :------------- | :------------ |
|Content-Type|application/x-www-form-urlencoded|
| deviceId     | 唯一Id    |

`请求参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|username    |用户名|
|password   |密码|
|imageCode   |验证码|

`响应参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|access_token    |访问token|
|token_type   |token类型|
|refresh_token   |刷新access_token的token|
|expires_in   |access_token有效期，单位：s|
|scope   |scope|

`返回示例`：
```
{
    "access_token": "f7b8f52c-cbca-438b-a0bb-f5f5e65655c6",
    "token_type": "bearer",
    "refresh_token": "627bb9c2-f13a-4779-ad28-aa6b141bbe95",
    "expires_in": 3599,
    "scope": "all"
}
```

### 刷新token
`URL`：`/oauth/token?grant_type=refresh_token&refresh_token=cb3b42a8-fee5-48b2-9ae0-c9353139b588`

`请求方式`：`POST`

`请求参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|grant_type    |refresh_token|
|refresh_token   |授权登录返回的refresh_token|

`响应参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|access_token    |访问token|
|token_type   |token类型|
|refresh_token   |刷新access_token的token|
|expires_in   |access_token有效期，单位：s|
|scope   |scope|

`返回示例`：
```
{
    "access_token": "12b0e567-3eb8-412e-b16e-4349bbea62e7",
    "token_type": "bearer",
    "refresh_token": "cb3b42a8-fee5-48b2-9ae0-c9353139b588",
    "expires_in": 3599,
    "scope": "all"
}
```

## 验证码

### 图片验证码

`URL`：`/code/image?deviceId=1111111111`

`请求方式`：`GET`

`请求参数`：

| 参数名 | 说明 |
| :------------- | :------------ |
|deviceId    |唯一标识图片验证码|