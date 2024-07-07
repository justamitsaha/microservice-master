package com.saha.amit.client;

import com.saha.amit.dto.UserDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    @Value(("${user.service.url}"))
    public String userEndpoint;
    Log log = LogFactory.getLog(UserClient.class);
    @Autowired
    private WebClient.Builder webBuilder;


    public Mono<UserDto> getUserById(int id) {
        log.info(userEndpoint + "findById/{id}");
        return webBuilder.build().
                get()
                .uri(userEndpoint+"findById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class);
    }


//    private final WebClient webClient;
//
//    @Autowired
//    UserClient( @Value("${user.service.url}") String url) {
//        this.webClient = WebClient.builder()
//                .baseUrl(url)
//                .build();
//    }
//
//
//
//    public Mono<UserDto> getUserById(int id) {
//        return this.webClient
//                .get()
//                .uri("findById/{id}", id)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(UserDto.class);
//    }
}
