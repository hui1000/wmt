package com.walmart.config;

import com.walmart.core.domain.User;
import com.walmart.core.repository.UsersRepository;
import com.walmart.core.repository.UsersDbRepository;
import com.walmart.core.services.AuthServiceImp;
import com.walmart.core.services.AuthService;
import com.walmart.util.AppConfig;

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
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableJpaRepositories(basePackages = "com.walmart.core.repository")
@EnableTransactionManagement
// @ImportResource({"classpath:security.xml"})
public class CoreConfig {
    @Bean
    public DataSource dataSource() {
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
    public AuthService createService(UsersRepository repo) {
        return new AuthServiceImp(repo);
    }

    @Bean
    public UsersRepository createRepo() {
        return new UsersDbRepository();
    }

}
