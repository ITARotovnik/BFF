
**BFF (Backend For Frontend) for Users, Tickets, and Payment Services**


This project serves as a Backend For Frontend (BFF) built with Spring Boot to cater to users, tickets, and payment services. It includes two gateways—one for web clients and another for mobile clients. Users and payments services are using REST, the ticket service is using gRPC


**Techonologies used:**

- Springboot

  

**Endpoints:**

_Users Service:_

/web/users/ : get, post all users
/web/users/{id}: get user by id, delete or update user

/mobile/users/ : get, post all users
/mobile/users/{id}: get user by id, delete or update user


_Tickets Service:_

/web/tickets/ : get or post tickets
/web/tickets/{id}: get ticket by id, update or delete ticket

/mobile/tickets/ : get or post tickets
/mobile/tickets/{id}: get ticket by id, update or delete ticket

_Payment Service_

/web/payments/: get or post payment
/web/payment/{id}: get payment by id, update or delete payment

/mobile/payments/: get or post payment
/mobile/payment/{id}: get payment by id, update or delete payment
