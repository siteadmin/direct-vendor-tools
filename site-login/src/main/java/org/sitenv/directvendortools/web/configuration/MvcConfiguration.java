package org.sitenv.directvendortools.web.configuration;

import org.sitenv.directvendortools.web.util.SaltedPasswordHashUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan("org.sitenv.directvendortools.web")
// @PropertySource(value = { "classpath:environment.properties" })
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	/*
	 * @Override public void
	 * configureDefaultServletHandling(DefaultServletHandlerConfigurer
	 * configurer) { configurer.enable(); }
	 */

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SaltedPasswordHashUtil();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/dist/**").addResourceLocations("/dist/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
		registry.addResourceHandler("/vendor/**").addResourceLocations("/vendor/");
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:index.jsp");
		super.addViewControllers(registry);
	}
}
