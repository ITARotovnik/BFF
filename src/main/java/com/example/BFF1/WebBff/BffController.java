package com.example.BFF1.WebBff;


import com.example.BFF1.Dto.PaymentDTO;
import com.example.BFF1.Dto.TicketDTO;
import com.example.BFF1.Dto.UsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;

@RestController
@RequestMapping("/web/")
@CrossOrigin("*")
public class BffController {

    private static final Logger log = Logger.getLogger(BffController.class.toString());

    private final BffBusinessTickets business;
   private BffBusinessUsers businessUser;
   private BffBusinessPayments businessPayment;
    @Autowired
    public BffController(BffBusinessTickets business, BffBusinessUsers businessUser, BffBusinessPayments businessPayment){
        this.business = business;
        this.businessUser=businessUser;
        this.businessPayment=businessPayment;
    }


    ////////////////////////////////////////////USERS//////////////////////////////////////////////////////////
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

    @GetMapping("/users/{id}")
    public ResponseEntity<String> getUsersById(@PathVariable String id) {
        try {
            return businessUser.getUsersById(id);
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO user){
       return businessUser.createUser(user);
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

    @PutMapping("/users/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable String id , @RequestBody UsersDTO user){
        try{
            return businessUser.updateUser(id,user);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //////////////////////////////////////////////////PAYMENTS////////////////////////////////////////////////////////////
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

    @GetMapping("/payments/{id}")
    public ResponseEntity<String> getPaymentById(@PathVariable String id) {
        try {
            return businessPayment.getPaymentById(id);
        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO payment){
        return businessPayment.createPayment(payment);
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
    @PutMapping("/payments/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable String id , @RequestBody PaymentDTO payment){
        try{
            return businessPayment.updatePayment(id,payment);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /////////////////////////////////////////////////TICKETS////////////////////////////////////////////////////

    @GetMapping("tickets")
    ResponseEntity<?> getTickets(){
        var data = business.getAllTickets();
        return ResponseEntity.ok(BffResponse.builder().data(data).build());
    }

    @GetMapping("tickets/{id}")
    ResponseEntity<?> getTicketById(@PathVariable String id){
        var data = business.getTicketById(id);
        return ResponseEntity.ok(BffResponse.builder().data(data).build());
    }
    @PostMapping("tickets")
    ResponseEntity<?> createTicket (@RequestBody  TicketDTO ticket){

        var data = business.createTicket(ticket);
        return ResponseEntity.ok(BffResponse.builder().data(data).build());
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable String id, @RequestBody TicketDTO ticket) {
        try {
            TicketDTO updatedTicket = business.updateTicket(id, ticket);
            if (updatedTicket != null) {
                return ResponseEntity.ok(updatedTicket);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @DeleteMapping("/tickets/{id}")
    ResponseEntity<?> removeTicket(@PathVariable String id) {
        business.removeTicket(id);
        return ResponseEntity.ok(BffResponse.builder().message("Ticket deleted succesfully").build());
    }
}
