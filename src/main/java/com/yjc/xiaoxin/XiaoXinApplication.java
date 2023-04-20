package com.yjc.xiaoxin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
public class XiaoXinApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoXinApplication.class,args);
        log.info("springboot项目启动成功");
    }
}
