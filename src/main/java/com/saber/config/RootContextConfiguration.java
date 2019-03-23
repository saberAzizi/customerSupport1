package com.saber.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
@ComponentScan(
        basePackages = "com.saber.site",
        excludeFilters = @ComponentScan.Filter(Controller.class)
)
@EnableJpaRepositories(
        basePackages = "com.saber.site.repositories",
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "jpaTransactionManagerFactoryBean"
)
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class RootContextConfiguration {
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource=
                new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setCacheMillis(-1);
        messageSource.setBasenames(
                "/WEB-INF/i18n/validation",
                "/WEB-INF/i18n/errors",
                "/WEB-INF/i18n/messages",
                "/WEB-INF/i18n/titles");
     return messageSource;
    }
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean (){
        LocalValidatorFactoryBean localValidatorFactoryBean =
                new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(this.messageSource());
        return localValidatorFactoryBean;
    }
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(this.localValidatorFactoryBean());
        return methodValidationPostProcessor;
    }
    @Bean
    public DataSource dataSource(){
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource("jdbc/mysql9");
    }

    private Properties hibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("javax.persistence.schema-generation.database.action","none");
        hibernateProperties.put("hibernate.show_sql","true");
        hibernateProperties.put("hibernate.format_sql","true");
        hibernateProperties.put("hibernate.search.default.directory-provider","filesystem");
        hibernateProperties.put("hibernate.search.default.indexBase","../searchIndexes");
        return hibernateProperties;
    }

    private HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(this.dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.saber.site.model");
        entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        entityManagerFactoryBean.setValidationMode(ValidationMode.NONE);
        entityManagerFactoryBean.setJpaProperties(this.hibernateProperties());
        entityManagerFactoryBean.setJpaVendorAdapter(this.hibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }
    @Bean
    public PlatformTransactionManager jpaTransactionManagerFactoryBean(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());
        return transactionManager;
    }
}
