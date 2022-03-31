# Steal Bomber

> 这是一个用Java语言写的盗号网站轰炸机

## 启动软件

- 在终端执行 `java --enable-preview -jar stealbomber.jar`
- 程序将自动输出默认配置文件(如果没有)
- 输出将显示在**控制台**上

### 启动参数

支持一个启动参数

| 参数 | 说明 | 例子 |
| --- | --- | --- |
| -Dfile= | 自定义配置名称(位置) | java --enable-preview -Dfile="example.properties" -jar stealbomber.jar |

## 文本编写

> 默认使用自动生成的 default.properties

使用Java Properties写法

一行只能有 `参数` 和 `参数值` ， 中间使用**等号**隔开

| 参数名 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- |
| threads | 否 | 16 | 并行攻击的线程数量 |
| method | 否 | POST | 支持所有的HTTP请求方法 |
| URL | 是 | - | 发送账号密码的网址，多个URL用 `,` 隔开([见下例](#多个攻击网址写法)) |
| parameter | 是 | - | 向网站发送的参数([见下例](#parameter写法)) |
| URL | 是 | - | 发送账号密码的网址 |
| proxyfile | 否 | - | 代理文件地址 |

功能开关

| 参数名 | 默认值 | 说明 |
| --- | --- | --- |
| genoutput | false | 控制台输出随机生成的账号密码 |
| proxyswitch | false | 使用代理攻击 |


### 多个攻击网址写法

> 无限制攻击网址个数

比如有 `https://a.a.a` 和 `http://b.b.b/` 和 `https://c.c.c` 需要写入，这样写即可

```
URL=https://a.a.a,http://b.b.b/,https://c.c.c
```

### parameter写法

> 例: `username=$[account]&pass=$[password]`

| 变量名 | 说明 |
| --- | --- |
| $[account] | 随机生成QQ号,QQ邮箱,中国大陆手机号 |
| $[password] | 随机密码 |

### 代理文件

代理支持 `HTTP` `HTTPS` `SOCKS4` `SOCKS5` 类型

每一行一个代理，格式如下例

```
https://5.2.3.5:5235
socks4://1.1.1.1:1111
https://1.2.3.4:5678
socks5://6.6.6.6:2345
```

## TODO

- [x] 支持代理
- [x] 支持多个URL同时攻击
- [ ] 添加GUI
