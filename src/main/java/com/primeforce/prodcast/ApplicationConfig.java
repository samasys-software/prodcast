package com.primeforce.prodcast;

import javax.inject.Named;

import com.primeforce.prodcast.dao.DatabaseManager;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration

public class ApplicationConfig {

	@Named
	static class JerseyConfig extends ResourceConfig {
		public JerseyConfig() {
			this.packages("com.primeforce.prodcast");
			this.packages("com.primeforce.prodcast").register(MultiPartFeature.class);
		}
	}

}