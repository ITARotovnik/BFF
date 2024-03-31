package com.example.BFF1.MobileBff;

import com.example.BFF1.WebBff.BffBusinessPayments;
import com.example.BFF1.WebBff.BffBusinessTickets;
import com.example.BFF1.WebBff.BffBusinessUsers;
import com.example.BFF1.WebBff.BffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/mobile/")
@CrossOrigin("*")
public class MobileBffController {

    private final BffBusinessTickets business;
    private BffBusinessUsers businessUser;
    private BffBusinessPayments businessPayment;

    @Autowired
    public MobileBffController(BffBusinessTickets business, BffBusinessUsers businessUser, BffBusinessPayments businessPayment){
        this.business = business;
        this.businessUser=businessUser;
        this.businessPayment=businessPayment;
    }

    ////////////////////////////////////////////////////////////USERS/////////////////////////////////////////////////////
    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        try {
            return businessUser.getUsers();
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            businessUser.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with ID " + id + " was deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user with ID " + id + ". Error: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////PAYMENTS/////////////////////////////////////////////////
    @GetMapping("/payments")
    public ResponseEntity<String> getPayments() {
        try {
            return businessPayment.getPayments();
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable String id) {
        try {
            id = "6607db41e83cee2c4e818b0e";
            businessPayment.deletePayment(id);
            return ResponseEntity.status(HttpStatus.OK).body("Payment with ID " + id + " was deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete payment with ID " + id + ". Error: " + e.getMessage());
        }
    }

    ////////////////////////////////////////////////////TICKETS/////////////////////////////////////////////////
    @GetMapping("tickets")
    ResponseEntity<?> getTickets(){
        var data = business.getAllTickets();
        return ResponseEntity.ok(BffResponse.builder().data(data).build());
    }

    @DeleteMapping("/tickets/{id}")
    ResponseEntity<?> removeTicket(@PathVariable String id) {
        business.removeTicket(id);
        return ResponseEntity.ok(BffResponse.builder().build());
    }
}
