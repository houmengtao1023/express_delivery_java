# express-delivery（寄快递后端）

uni-app 小程序（`express_delivery`）配套的 Java 后端，使用 Spring Boot 2.7（JDK 8 起步）。
当前版本数据全部存放在内存（`ConcurrentHashMap`），重启会清空，方便本地联调。

## 接口列表

统一前缀：`http://localhost:8088/api`

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET  | `/address/list?keyword=` | 地址簿列表，支持关键词模糊检索 |
| GET  | `/address/{id}`          | 地址详情 |
| POST | `/address`               | 新增/修改地址（带 id 即修改） |
| PUT  | `/address/{id}/default`  | 设为默认寄件地址 |
| DELETE | `/address/{id}`        | 删除地址 |
| GET  | `/station/list?type=`    | 服务网点列表（type=便民站/营业部） |
| GET  | `/station/nearest?type=` | 最近的服务网点 |
| GET  | `/order/pickup-slots`    | 上门取件时间槽（今天/明天/后天） |
| POST | `/order/estimate`        | 运费预估 |
| POST | `/order`                 | 下单 |
| GET  | `/order/list`            | 我的订单列表 |
| GET  | `/order/{id}`            | 订单详情 |

统一响应：

```json
{ "code": 0, "msg": "ok", "data": { ... } }
```

## 启动方式

> 本机已经准备好：
> - JDK 8（`/Library/Java/JavaVirtualMachines/zulu-8.jdk`）
> - Maven 3.9.15（`~/maven/apache-maven-3.9.15`，settings.xml 已配置阿里云镜像）

### 方式一：Cursor 内置终端一键启动（推荐）

在 Cursor 顶部菜单 `Terminal → New Terminal`（快捷键 `⌃ + ~`）打开**用户终端**（注意不是 AI 聊天工具调用），粘贴：

```bash
bash /Users/houmengtao/Zaizhi/xiangmu/kuaidi/express_delivery_java/start.sh
```

首次会下载 Spring Boot 全量依赖（约 1–2 分钟，存到 `~/maven/repo`，避免污染系统 `~/.m2`），看到：

```
Tomcat started on port(s): 8088 (http) with context path '/api'
Started ExpressApplication in x.xxx seconds
```

即启动成功。打开浏览器或 curl `http://localhost:8088/api/address/list` 应能看到 JSON。

### 方式二：IDEA 启动

1. IDEA 打开 `express_delivery_java` 目录（识别为 Maven 项目）。
2. 等待依赖下载完成。
3. 运行 `com.lazy.express.ExpressApplication#main`。

## 前端联调

`express_delivery/src/utils/request.js` 中 `BASE_URL = http://localhost:8088/api`。

> 微信小程序基础库需要在「微信开发者工具 - 详情 - 本地设置」勾选「不校验合法域名、HTTPS 证书等」，
> 或在小程序后台配置合法域名后再访问。

## 包结构

```
com.lazy.express
├── common         # 统一响应 R / 业务异常 / 全局异常处理
├── config         # WebConfig（CORS）
├── controller     # 接口入口
├── service        # 业务实现（内存）
├── entity         # 领域对象
├── dto            # 入参
└── vo             # 出参
```
