package com.example.log;

//import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.example.log.config.StudentConfig;
import com.example.log.vo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@Slf4j
@EnableDiscoveryClient
//@NacosPropertySource(dataId = "log",autoRefreshed = true)
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
        log.info("----------------Log启动---------------------");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
        Student student = (Student) context.getBean("getInfo");
        System.out.println(student);
    }

}
