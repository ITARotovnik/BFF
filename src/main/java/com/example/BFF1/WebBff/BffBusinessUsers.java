package com.example.BFF1.WebBff;

import com.example.BFF1.Dto.UsersDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BffBusinessUsers {

    @Value("http://localhost:8081")
    private String usersUrl;

    public void UsersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getUsers() {
        return restTemplate.getForEntity(usersUrl + "/users", String.class);
    }
    public ResponseEntity<String> getUsersById(String id){
        return restTemplate.getForEntity(usersUrl+"/users/" + id,String.class);
    }

    public ResponseEntity<UsersDTO> createUser(UsersDTO user) {
        return restTemplate.postForEntity(usersUrl + "/users", user, UsersDTO.class);
    }

    public void deleteUser(String id) {
        restTemplate.delete(usersUrl + "/users/" + id);
    }

    public ResponseEntity<UsersDTO> updateUser(String id, UsersDTO user) {
        String url = usersUrl + "/users/" + id;
        HttpEntity<UsersDTO> requestEntity = new HttpEntity<>(user);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
            return ResponseEntity.ok(user);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
