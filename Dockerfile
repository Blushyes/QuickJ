FROM openjdk:17

# 设置容器名称和重启策略
ARG CONTAINER_NAME=quickj
ARG RESTART="always"

# 暴露端口
EXPOSE 8888

# 挂载本地文件系统到容器中
ADD ./QuickJ-0.0.1-SNAPSHOT.jar app.jar

# 设置环境变量
ENV TZ=Asia/Shanghai

# 启动命令
CMD java -jar app.jar