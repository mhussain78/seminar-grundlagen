package com.mhussain.studium.seminargrundlagen;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collection;

import static java.util.Collections.singletonList;

@EnableTransactionManagement
@ComponentScan
@Configuration
public class StandardSpringApplication {

    @Bean
    PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager tm) {
        return new TransactionTemplate(tm);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = doRun();
        SerminarteilnehmerService seminarteilmehmerService = applicationContext.getBean(SerminarteilnehmerService.class);
        Seminarteilnehmer seminarteilnehmer = new Seminarteilnehmer();
        seminarteilnehmer.setName("Mohamad Hussain");
        seminarteilnehmer.setMatrikelNummer(7502834L);
        seminarteilnehmer.setSeminar("Spring Reactive Programming");
        seminarteilnehmer.setEmail("inf.mhussain@googlemail.com");
        seminarteilmehmerService.save(singletonList(seminarteilnehmer));
        Collection<Seminarteilnehmer> seminarteilnehmers = seminarteilmehmerService.findAll();
        Assert.isTrue(seminarteilnehmers.size() == 1, "Die Anzahl der Teilnehmer ist falsch.");
    }

    private static ConfigurableApplicationContext doRun() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(StandardSpringApplication.class);
        applicationContext.refresh();
        applicationContext.start();
        return applicationContext;
    }

}