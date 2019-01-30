package com.wyg;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
//http://archive.apache.org/dist/tomcat/tomcat-connectors/native/

/**
 * Application启动类的位置不对.要将Application类放在最外侧,即包含所有子包
 原因:spring-boot会自动加载启动类所在包下及其子包下的所有组件.
 * 是@SpringBootConfiguration,@EnableAutoConfiguration,@ComponentScan的结合。
 * @SpringBootConfiguration与@Configuration相同，表示类是IOC容器的配置类
 * @EnableAutoConfiguration用于将所有符合自动配置的Bean加载到当前SpringBoot创建并使用的IOC容器中
 * @ComponentScan用于自动扫描和加载符合条件的组件或Bean，并将Bean加载到IOC容器中
 */
@EnableTransactionManagement //添加事务管理 在需要事务的类上加上@Transactional就可以了
@SpringBootApplication
@MapperScan("com.wyg.dao")
public class DemoApplication implements TransactionManagementConfigurer{
	@Resource(name="txManager1")
	private PlatformTransactionManager txManager1;

	// 创建事务管理器1
	@Bean(name = "txManager1")
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 创建事务管理器2
	@Bean(name = "txManager2")
	public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	// 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager1;
	}

	public static void main(String[] args) {
		/**
		 * SpringApplication实例初始化：
		 * 1.根据classpath内是否存在某个特征类来判断是否为Web应用，并使用webEnvironment标记是否为web应用。
		 * 2.使用SpringFactoriesLoader在classpath中的spring.factories文件查找并加载所有可用的ApplicationContextInitializer.
		 * 3.使用SpringFactoriesLoader在classpath中的spring.factories文件查找并加载所有可用的ApplicationListener.
		 * 4.推断并设置main()方法的定义类。
		 */
		/**
		 * 调用实例的run()方法：
		 * 1.查找并加载spring.factories中配置的SpringApplicationRunListener，并调用他们的started()方法。
		 * 告诉SpringApplicationRunListener,spring Boot应该要执行了。
		 * 2.创建并胚子当前SpringBoot应用要使用的Environment,然后遍历调用所用SpringApplicationRunlistener的
		 * environmentPrepared()方法。告诉SpringBoot应用的环境准备好了。
		 * 3.如果SpringAPP李擦缇欧你的showBanner属性被设置为true,则打印banner.
		 * 4.根据用户是否明确设置了applicationContextClass类型，以及初始化阶段的推断结果来决定
		 * 当前SpringBoot应用创建什么类型的ApplicationContext。
		 * 5.创建故障分析器。提供错误和诊断信息。
		 * 6.对ApplicationContext进行后置处理。
		 * 7.调用refreshContext()方法执行applicationContext的refresh()方法。
		 * 8.查找当前ApplicationContext中是否有ApplicationRunner和CommandLineRunner,如果有，则遍历执行他们。
		 * 9.遍历SpringApplicationRunListener的finished()方法。
		 */
		SpringApplication.run(DemoApplication.class, args);
	}

}

