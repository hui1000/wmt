package com.walmart.config;

import com.walmart.util.AppConfig;
import com.walmart.core.domain.Item;
import com.walmart.core.repository.ItemsRepository;
import com.walmart.core.repository.StoresRepository;
import com.walmart.core.repository.ItemsDbRepository;
import com.walmart.core.repository.StoresDbRepository;
import com.walmart.core.repository.UsersRepository;
import com.walmart.core.repository.UsersDbRepository;


import com.walmart.core.services.ItemServiceImp;
import com.walmart.core.services.ItemService;
import com.walmart.core.services.AuthService2Imp;
import com.walmart.core.services.AuthService2;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@Configuration
@EnableJpaRepositories(basePackages = "com.walmart.core.repository")
@EnableTransactionManagement
public class CoreConfig {
    @Bean
    public DataSource dataSource() {
        /*
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
        */
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
        dataSource.setDriverClassName(AppConfig.getProperty("db_driver"));
        dataSource.setUrl(AppConfig.getProperty("db_url"));
        dataSource.setUsername(AppConfig.getProperty("db_user"));
        dataSource.setPassword(AppConfig.getProperty("db_password"));
 
        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // vendorAdapter.setDatabase(Database.HSQL);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.walmart.core.domain");
        factory.setDataSource(dataSource());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public ItemService createService(ItemsRepository itemRepo, StoresRepository storeRepo) {
        return new ItemServiceImp(itemRepo, storeRepo);
    }

    @Bean
    public ItemsRepository createRepo() {
        return new ItemsDbRepository();
    }

    @Bean
    public StoresRepository createStoreRepo() {
        return new StoresDbRepository();
    }

    @Bean
    public UsersRepository createUserRepo() {
        return new UsersDbRepository();
    }

    @Bean
    public AuthService2 createAuthService2(UsersRepository userRepo) {
        return new AuthService2Imp(userRepo);
    }
}
