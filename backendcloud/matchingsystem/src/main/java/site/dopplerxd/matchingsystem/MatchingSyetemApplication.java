package site.dopplerxd.matchingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.dopplerxd.matchingsystem.service.impl.MatchingServiceImpl;

@SpringBootApplication
public class MatchingSyetemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start(); // 启动匹配线程
        SpringApplication.run(MatchingSyetemApplication.class, args);
    }
}