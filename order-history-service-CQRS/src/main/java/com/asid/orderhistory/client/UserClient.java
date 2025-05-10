package com.asid.orderhistory.client;

import com.asid.orderhistory.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public UserDTO getUser(Long userId) {
        return restTemplate.getForObject("http://userms/users/" + userId, UserDTO.class);
    }

    public String getUserEmail(Long userId) {
        UserDTO user = getUser(userId);
        return user != null ? user.getEmail() : null;
    }
}
