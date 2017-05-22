package com.retailmanager.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Created by Alok on 23-05-2017.
 */
@ComponentScan({"com.retailmanager.*"})
@SpringBootApplication
public class RetailManagerLauncher{

        public static void main(String[] args) {
            SpringApplication.run(RetailManagerLauncher.class, args);
        }

}
