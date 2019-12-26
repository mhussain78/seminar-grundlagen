package com.mhussain.studium.seminargrundlagen;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;

// Spring Konfiguration, welche ohne Bedingung aktiviert ist.
@Configuration
public class DataSourceConfiguration {

    // Spring Konfiguration, die nur beim Aktivieren des prod Profiles vorhanden ist.
    @Configuration
    @Profile("prod")
    @PropertySource("application-prod.properties")
    public static class ProductionConfiguration {

        @Bean
        DataSource productionDataSource(@Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String username,
                                        @Value("${spring.datasource.password}") String password,
                                        @Value("${spring.datasource.driver-class-name}") Class<Driver> driverClass) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
            dataSource.setDriverClassName(driverClass.getName());
            return dataSource;
        }

    }

    // Spring Konfiguration, die nur beim Aktivieren des Default Profiles oder ohne Angabe eines speziellen Profiles vorhanden ist.
    @Configuration
    @Profile("default")
    public static class DevelopmentConfiguration {

        @Bean
        DataSource developmentDataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

    }

    // Eine Bean Instanz für eine Implementierung von BeanPostProcessor, dessen Methode postProcessAfterInitialization nach Erzeugen jeder Bean Instanz aufgerunfen wird.
    // Falls es sich bei der übergebenen Instanz um eine Datesource handelt, wird DatabaseInitializer#initialize aufgerufen, um die Datenbank zu initialisieren.
    @Bean
    DataSourcePostProcessor dataSourcePostProcessor() {
        return new DataSourcePostProcessor();
    }

    private static class DataSourcePostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof DataSource) {
                DatabaseInitializer.initialize(DataSource.class.cast(bean));
            }
            return bean;
        }

    }

}