package com.sheldon.apibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/sheldon-3601e">Sheldon</a>
 * @from <a href="https://github.com/sheldon-3601e">github</a>
 */

@SpringBootApplication
@MapperScan("com.sheldon.apibackend.mapper")
@EnableDubbo
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
