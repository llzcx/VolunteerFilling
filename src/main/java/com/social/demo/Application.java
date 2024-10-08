package com.social.demo;

import com.social.demo.util.JVMUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.InputStream;

/**
 * @author 陈翔
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class},scanBasePackages={"com.social.demo.*"})
@EnableTransactionManagement
public class Application {

	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		JVMUtil.printJVM();
		applicationContext = SpringApplication.run(Application.class, args);
	}

	/**
	 * 获取bean对象
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static  <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}
}
