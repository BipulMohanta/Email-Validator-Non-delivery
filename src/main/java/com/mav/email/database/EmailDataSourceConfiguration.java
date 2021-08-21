package com.mav.email.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mav.email.IndexApplication;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class EmailDataSourceConfiguration {

	@Autowired
	private EmailDBProperties emailDBProperties;

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource createDataSource() throws Exception {

		if (StringUtils.isBlank(emailDBProperties.getUrl()) || StringUtils.isBlank(emailDBProperties.getPassword())
				|| StringUtils.isBlank(emailDBProperties.getDriverClassName())
				|| StringUtils.isBlank(emailDBProperties.getUsername())) {
			throw new Exception();
		}

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(emailDBProperties.getDriverClassName());
		hikariConfig.setJdbcUrl(emailDBProperties.getUrl());
		hikariConfig.setUsername(emailDBProperties.getUsername());
		hikariConfig.setPassword(emailDBProperties.getPassword());

		return new HikariDataSource(hikariConfig);

	}

	@Bean(name = "emailEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) throws Exception {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties jpaProperties = new Properties();

		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.jpa.database-platform"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.show_sql"));
		jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("spring.format_sql"));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		entityManagerFactoryBean.setPackagesToScan(IndexApplication.class.getPackage().getName());
		entityManagerFactoryBean.setPersistenceUnitName("emailDB");
		return entityManagerFactoryBean;

	}

	@Bean(name = "emailTxManager")
	public JpaTransactionManager emailTxManager(
			@Qualifier("emailEntityManager") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return jpaTransactionManager;

	}
}
