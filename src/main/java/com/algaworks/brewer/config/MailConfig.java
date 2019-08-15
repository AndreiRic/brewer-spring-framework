package com.algaworks.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySources({
	@PropertySource({ "classpath:env/mail-${ambiente:local}.properties" }),
	@PropertySource(value = { "file:${USERPROFILE}/brewer-mail.properties" }, ignoreResourceNotFound = true)
})
public class MailConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(465);
		mailSender.setUsername(env.getProperty("email.username"));
		mailSender.setPassword(env.getProperty("EMAIL_PASSWORD"));
		
		Properties props = new Properties();
/*		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // milisegundos
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
*/		
   	    props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
         
		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}
	
}
