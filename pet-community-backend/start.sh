#!/bin/bash

# 宠物社区应用启动脚本

echo "======================================"
echo "宠物社区应用后端系统启动脚本"
echo "======================================"

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: 未检测到Java环境，请先安装JDK 17+"
    exit 1
fi

echo "Java版本:"
java -version

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "错误: 未检测到Maven，请先安装Maven 3.8+"
    exit 1
fi

echo ""
echo "Maven版本:"
mvn -v

# 选择启动方式
echo ""
echo "请选择启动方式:"
echo "1. 开发模式 (使用maven直接运行)"
echo "2. 生产模式 (打包后运行jar)"
echo "3. Docker模式 (使用docker-compose)"
read -p "请输入选项 (1/2/3): " choice

case $choice in
    1)
        echo ""
        echo "======================================"
        echo "启动开发模式..."
        echo "======================================"
        mvn spring-boot:run -Dspring-boot.run.profiles=dev
        ;;
    2)
        echo ""
        echo "======================================"
        echo "打包项目..."
        echo "======================================"
        mvn clean package -DskipTests

        if [ $? -eq 0 ]; then
            echo ""
            echo "======================================"
            echo "启动应用..."
            echo "======================================"
            java -jar target/pet-community-backend-1.0.0.jar --spring.profiles.active=prod
        else
            echo "打包失败！"
            exit 1
        fi
        ;;
    3)
        echo ""
        echo "======================================"
        echo "使用Docker Compose启动..."
        echo "======================================"

        # 检查Docker
        if ! command -v docker &> /dev/null; then
            echo "错误: 未检测到Docker，请先安装Docker"
            exit 1
        fi

        # 先打包
        echo "正在打包项目..."
        mvn clean package -DskipTests

        if [ $? -eq 0 ]; then
            echo ""
            echo "启动Docker服务..."
            docker-compose up -d

            echo ""
            echo "查看服务状态:"
            docker-compose ps
        else
            echo "打包失败！"
            exit 1
        fi
        ;;
    *)
        echo "无效的选项！"
        exit 1
        ;;
esac

echo ""
echo "======================================"
echo "启动完成！"
echo "API文档地址: http://localhost:8080/doc.html"
echo "======================================"
