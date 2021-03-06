package com.design.pension_system;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


@MapperScan(basePackages={"com.design.pension_system.**.mapper"})
@ForestScan(basePackages = "com.design.pension_system.sys.client")
@SpringBootApplication
public class PensionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PensionSystemApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
//允许上传的文件最大值
        factory.setMaxFileSize(DataSize.parse("50MB")); //KB,MB
/// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("50MB"));
        return factory.createMultipartConfig();
    }

}
