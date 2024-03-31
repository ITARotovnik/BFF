package com.example.BFF1.WebBff;

import com.example.BFF1.Dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BffBusinessPayments {


    @Value("http://localhost:8082")
    private String paymentsUrl;

    public void PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getPayments() {
        return restTemplate.getForEntity(paymentsUrl + "/payments", String.class);
    }
    public ResponseEntity<String> getPaymentById(String id){
        return restTemplate.getForEntity(paymentsUrl+"/payments/" + id,String.class);
    }

    public ResponseEntity<PaymentDTO> createPayment(PaymentDTO payment) {
        return restTemplate.postForEntity(paymentsUrl + "/payments", payment, PaymentDTO.class);
    }

    public void deletePayment(String id) {
        // Send an empty request body
        restTemplate.exchange(
                paymentsUrl + "/payments/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(Collections.emptyMap()),
                String.class
        );
    }


    public ResponseEntity<PaymentDTO> updatePayment(String id, PaymentDTO payment) {
        String url = paymentsUrl + "/payments/" + id;
        HttpEntity<PaymentDTO> requestEntity = new HttpEntity<>(payment);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
            return ResponseEntity.ok(payment);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
