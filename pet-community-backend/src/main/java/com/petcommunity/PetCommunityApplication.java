package com.petcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 宠物社区应用启动类
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@SpringBootApplication
@MapperScan("com.petcommunity.mapper")
public class PetCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetCommunityApplication.class, args);
        System.out.println("====================================");
        System.out.println("宠物社区应用后端系统启动成功!");
        System.out.println("API文档地址: http://localhost:8080/doc.html");
        System.out.println("====================================");
    }

}
