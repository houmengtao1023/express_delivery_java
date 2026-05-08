#!/usr/bin/env bash
set -e

# 寄快递后端一键启动脚本（macOS）
# 1) 使用本机已装的 Zulu JDK 8
# 2) 使用我刚才下载到 ~/maven 的 Apache Maven 3.9.15（已配置阿里云镜像）
# 3) 启动 Spring Boot，监听 http://localhost:8088/api

export JAVA_HOME="/Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

MVN_HOME="$HOME/maven/apache-maven-3.9.15"
MVN_REPO="$HOME/maven/repo"

if [ ! -x "$MVN_HOME/bin/mvn" ]; then
  echo "[error] 找不到 Maven：$MVN_HOME，请重新下载 Maven 二进制包。" >&2
  exit 1
fi

if [ ! -x "$JAVA_HOME/bin/java" ]; then
  echo "[error] 找不到 JDK：$JAVA_HOME" >&2
  exit 1
fi

mkdir -p "$MVN_REPO"

cd "$(cd "$(dirname "$0")" && pwd)"

echo "[info] JAVA_HOME = $JAVA_HOME"
echo "[info] MAVEN     = $MVN_HOME"
echo "[info] LOCAL_REPO= $MVN_REPO"
echo "[info] starting spring-boot:run ..."

exec "$MVN_HOME/bin/mvn" -B \
  -Dmaven.repo.local="$MVN_REPO" \
  -DskipTests \
  spring-boot:run
