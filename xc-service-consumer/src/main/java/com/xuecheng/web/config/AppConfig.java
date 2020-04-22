package com.xuecheng.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
@ConfigurationProperties(prefix = "fastdfs")
@Data
public class AppConfig {
	
	private String connect_timeout_in_seconds;
	private String network_timeout_in_seconds;
	private String charset;
	private String http_anti_steal_token;
	private String http_secret_key;
	private String http_tracker_http_port;
	private String tracker_servers;
	
	//@Bean
	public Properties getP() {
		Properties p = new Properties();
		p.setProperty("fastdfs.connect_timeout_in_seconds",connect_timeout_in_seconds);
		p.setProperty("fastdfs.network_timeout_in_seconds",network_timeout_in_seconds);
		p.setProperty("fastdfs.charset",charset);
		p.setProperty("fastdfs.http_anti_steal_token",http_anti_steal_token);
		p.setProperty("fastdfs.http_secret_key",http_secret_key);
		p.setProperty("fastdfs.http_tracker_http_port",http_tracker_http_port);
		p.setProperty("fastdfs.tracker_servers",tracker_servers);
		return p;
	}
}
