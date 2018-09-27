package com.lvbin.client;

import feign.*;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {

    private Logger logger = LoggerFactory.getLogger(ClientApplication.class);
    private int Count = 0;

    private interface MyServer {
        @RequestLine("POST /ip")
        @Headers("Content-Type: application/json")
        @Body("%7B\"text\": \"{text}\"%7D")
        void contributors(@Param("text") String text);
    }

    @Scheduled(cron = "0/5 * * * * *")  //cron接受cron表达式，根据cron表达式确定定时规则
    void ConnectToServer() {
        MyServer myServerPost = Feign.builder().decoder(new GsonDecoder()).encoder(new GsonEncoder()).target(MyServer.class, "http://47.98.216.143:12345");
        myServerPost.contributors("woshinidaye");
        /*
        MyServer myServer = Feign.builder().target(MyServer.class, "http://47.98.216.143:12345");
        // Fetch and print a list of the contributors to this library.
        myServer.contributors();
        */
        logger.warn(String.format("Success for %d", ++Count));
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
