# Steal Bomb

> 这是一个用Java语言写的轰炸盗号网站软件

## 启动软件

- 在终端执行 `java -jar stealbomb.jar` 即可使用()shiy
- 输出将显示在**控制台**上

### 启动参数

支持一个启动参数

| 参数 | 说明 | 例子 |
| --- | --- | --- |
| -Dfile= | 自定义配置名称(位置) | java -Dfile=../example.properties -jar steal.jar|

## 文本编写

> 默认使用同一文件夹下的配置 default.properties(需自行下载: <https://cdn.jsdelivr.net/gh/ObcbO/stealbomb/default.properties>)

使用Java Properties写法

一行只能有 `参数` 和 `参数值` ， 中间使用**等号**隔开

| 参数名 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- |
| threads | 否 | 1 | 并行攻击的线程数量 |
| method | 否 | POST | 支持所有的HTTP请求方法 |
| URL | 是 | - | 发送账号密码的网址 |
| parameter | 是 | - | 向网站发送的参数 |

## TODO

- [ ] 支持多个URL同时攻击
- [ ] 添加GUI
