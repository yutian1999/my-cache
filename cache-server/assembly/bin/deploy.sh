#!/bin/bash

## 进入执行目录
cd `dirname $0`
BIN_DIR=`pwd`

# 构造启动命令
execCommand="sh server.sh $1 pro null"

echo '执行命令: '$execCommand

$execCommand