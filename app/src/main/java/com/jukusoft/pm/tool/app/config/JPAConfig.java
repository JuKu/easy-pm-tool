package com.jukusoft.pm.tool.app.config;

import com.jukusoft.pm.tool.def.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Profile("default")
public class JPAConfig {

    protected static final String CONF_DB_SECTION = "Database";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.jukusoft.pm.tool");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        String type = Config.get(CONF_DB_SECTION, "type");//postgresql

        String ip = Config.get(CONF_DB_SECTION, "host");
        int port = Config.getInt(CONF_DB_SECTION, "port");
        String user = Config.get(CONF_DB_SECTION, "user");
        String password = Config.get(CONF_DB_SECTION, "password");
        String dbName = Config.get(CONF_DB_SECTION, "database");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Config.get(CONF_DB_SECTION, "driver.class.name"));

        if (type.equals("sqlite")) {
            String fileName = Config.get(CONF_DB_SECTION, "sqlite_file");
            dataSource.setUrl("jdbc:sqlite:" + fileName + "/" + dbName);
        } else {
            dataSource.setUrl("jdbc:" + type + "://" + ip + ":" + port + "/" + dbName);
        }

        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", Config.get(CONF_DB_SECTION, "hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", Config.get(CONF_DB_SECTION, "hibernate.dialect"));

        //avoid this exception: java.sql.SQLFeatureNotSupportedException: Die Methode org.postgresql.jdbc4.Jdbc4Connection.createClob() ist noch nicht implementiert.
        properties.setProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", "true");

        return properties;
    }
}
