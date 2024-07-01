package org.example.publicdatacontest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
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

        Server server = new Server();
        server.setUrl("https://hyunwoo9930.shop");

        Server server2 = new Server();
        server2.setUrl("http://localhost:8080/");

        List<Server> serverList = new ArrayList<>();
        serverList.add(server);
        serverList.add(server2);

        return new OpenAPI()
                .info(info)
                .servers(serverList);
    }
}
