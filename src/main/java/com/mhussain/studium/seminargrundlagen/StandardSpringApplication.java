package com.mhussain.studium.seminargrundlagen;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collection;

import static java.util.Collections.singletonList;

@ComponentScan
@Configuration
@Import(DataSourceConfiguration.class)
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
        ConfigurableApplicationContext applicationContext = SpringSupport.run(StandardSpringApplication.class, null);
        SeminarteilmehmerService seminarteilmehmerService = applicationContext.getBean(SeminarteilmehmerService.class);
        Seminarteilmehmer seminarteilmehmer = new Seminarteilmehmer();
        seminarteilmehmer.setName("Mohamad Hussain");
        seminarteilmehmer.setMatrikelNummer(7502834L);
        seminarteilmehmer.setSeminar("Spring Reactive Programming");
        seminarteilmehmer.setEmail("inf.mhussain@googlemail.com");
        seminarteilmehmerService.save(singletonList(seminarteilmehmer));
        Collection<Seminarteilmehmer> seminarteilmehmers = seminarteilmehmerService.findAll();
        Assert.isTrue(seminarteilmehmers.size() == 1, "Die Anzahl der Teilnehmer ist falsch.");
    }

}