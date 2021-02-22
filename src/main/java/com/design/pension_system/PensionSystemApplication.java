package com.design.pension_system;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@MapperScan(basePackages={"com.design.pension_system.**.mapper"})
@ForestScan(basePackages = "com.design.pension_system.sys.client")
@SpringBootApplication
public class PensionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PensionSystemApplication.class, args);
    }

}
