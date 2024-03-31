package com.example.BFF1.WebBff;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.eventTicket.TicketServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configurations {

    private static final Logger logger = LoggerFactory.getLogger(Configurations.class);

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
    }
    @Bean
    public TicketServiceGrpc.TicketServiceBlockingStub ticketServiceBlockingStub(ManagedChannel channel) {
        logger.debug("Creating TicketServiceBlockingStub bean.");
        return TicketServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}

