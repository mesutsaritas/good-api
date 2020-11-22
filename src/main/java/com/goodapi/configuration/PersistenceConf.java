//package com.goodapi.configuration;
//
//import java.util.Properties;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
///**
// * @author msaritas
// *
// */
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.goodapi.repository")
//public class PersistenceConf {
//
//    /**
//     * TODO these parameters can be read from a different file
//     */
//
//    @Value("${spring.datasource.url}")
//    private String dataSourceUrl;
//
//    @Value("${spring.datasource.username}")
//    private String dataSourceUserName;
//
//    @Value("${spring.datasource.password}")
//    private String dataSourcePassword;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String dataSourceDriverClass;
//
//    @Bean
//    public DataSource dataSource(Environment env) throws Exception {
//        return createH2DataSource();
//    }
//
//    @Autowired
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        return initializer;
//    }
//
//    private DataSource createH2DataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(dataSourceUrl);
//        dataSource.setUsername(dataSourceUserName);
//        dataSource.setPassword(dataSourcePassword);
//        dataSource.setDriverClassName(dataSourceDriverClass);
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) throws Exception {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(Boolean.TRUE);
//        vendorAdapter.setShowSql(Boolean.TRUE);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setPersistenceUnitName("customer");
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.goodapi.model");
//        factory.setDataSource(dataSource(env));
//
//      //  factory.setJpaProperties(jpaProperties());
//        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
//
//        return factory;
//    }
//
//    Properties jpaProperties() {
//        Properties props = new Properties();
//        props.put("hibernate.query.substitutions", "true 'Y', false 'N'");
//        props.put("hibernate.hbm2ddl.auto", "create-drop");
//        props.put("hibernate.show_sql", "false");
//        props.put("hibernate.format_sql", "true");
//        return props;
//    }
//
//}
