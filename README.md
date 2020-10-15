# my-cache

## 纯Java应用

### 轻量机分布式缓存


## 功能

### 2.0

- 支持主从模式


### 1.0

- 独立部署不与应用jvm耦合，可在分布式系统中全局使用
- 支持key过期时间
- 支持分布式锁
- 支持持久化
- 支持服务端远程调用&本地jar依赖


## 部署

package cache-server

cache-server-physical.tar.gz

tar -zxvf cache-server-physical.tar.gz

cd cache-server

sh server.sh start dev null
