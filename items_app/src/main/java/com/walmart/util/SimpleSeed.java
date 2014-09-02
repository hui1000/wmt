package com.walmart.util;

import java.io.*;
import java.net.*;
import java.util.*;
import com.walmart.core.repository.StoresJpaRepository;
import com.walmart.core.domain.Store;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJpaRepositories(basePackages = "com.walmart.core.repository")
public class SimpleSeed
{
    static public void main(String [] args){
        ConfigurableApplicationContext context = SpringApplication.run(SimpleSeed.class);
        StoresJpaRepository storeRepository = context.getBean(StoresJpaRepository.class);

        System.out.println("hi");
        for(int i =0; i < 2 ; i++)
        {
            Store s = new Store();
            s.setName("store " + i);
            storeRepository.save(s);
        }
    }
}
