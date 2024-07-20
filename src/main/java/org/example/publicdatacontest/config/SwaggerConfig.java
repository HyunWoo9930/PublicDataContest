package org.example.publicdatacontest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		Info info = new Info()
			.title("User Management API")
			.version("1.0")
			.description("API for managing users and their profile images.")
			.contact(new Contact()
				.name("HyunWoo9930")
				.email("hw62459930@gmail.com"));

		// Server server = new Server();
		// server.setUrl("https://hyunwoo9930.shop");

		Server server2 = new Server();
		server2.setUrl("http://localhost:8080/");

		Server server3 = new Server();
		server2.setUrl("http://54.180.217.161:8080/");

		List<Server> serverList = new ArrayList<>();
		// serverList.add(server);
		serverList.add(server2);
		serverList.add(server3);

		return new OpenAPI()
			.info(info)
			.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
			.components(new io.swagger.v3.oas.models.Components()
				.addSecuritySchemes("Bearer Authentication", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")));
	}
}
