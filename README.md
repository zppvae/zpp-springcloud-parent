# zpp-springcloud-parent

## 项目介绍
springcloud集成Eureka、Zuul、config、oauth2.0

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

#### 登录
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
