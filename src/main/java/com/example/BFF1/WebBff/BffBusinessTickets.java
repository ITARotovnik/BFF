package com.example.BFF1.WebBff;


import com.example.BFF1.Dto.TicketDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.eventTicket.TicketOuterClass;
import org.eventTicket.TicketServiceGrpc;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class BffBusinessTickets {

    @GrpcClient("ticket")
    private TicketServiceGrpc.TicketServiceBlockingStub ticketClient;
    public BffBusinessTickets(TicketServiceGrpc.TicketServiceBlockingStub ticketClient) {
        this.ticketClient = ticketClient;
    }

    public List<TicketDTO> getAllTickets(){
        if (ticketClient == null) {
            throw new IllegalStateException("Error: gRPC client is not initialized");
        }
        var response = ticketClient.getAllTickets(TicketOuterClass.Empty.newBuilder().build());
        return response.getTicketsList().stream().map(
                item -> TicketDTO.builder()
                        .id(item.getId())
                        .eventName(item.getEventname())
                        .eventPrice(item.getEventprice())
                        .eventDate(item.getEventdate())
                        .quantity(item.getQuanitity())
                        .build()).collect(Collectors.toList());
    }

    TicketDTO getTicketById(String id){
        var response = ticketClient.getTicketById(TicketOuterClass.TicketRequestId
                .newBuilder()
                .setId(id)
                .build());

        return TicketDTO.builder()
                .id(response.getId())
                .eventName(response.getEventname())
                .eventPrice(response.getEventprice())
                .eventDate(response.getEventdate())
                .quantity(response.getQuanitity())
                .build();
    }

    TicketOuterClass.Ticket createTicket(TicketDTO ticket){

        TicketOuterClass.Ticket request = TicketOuterClass.Ticket.newBuilder()
                .setEventname(ticket.getEventName())
                .setEventdate(ticket.getEventDate())
                .setEventprice(ticket.getEventPrice())
                .setQuanitity(ticket.getQuantity())
                .build();

        var response = ticketClient.createTicket(request);
        return response;
    }
    TicketDTO updateTicket(String id, TicketDTO updatedTicket) {
        TicketOuterClass.Ticket request = TicketOuterClass.Ticket.newBuilder()
                .setId(id)
                .setEventname(updatedTicket.getEventName())
                .setEventdate(updatedTicket.getEventDate())
                .setEventprice(updatedTicket.getEventPrice())
                .setQuanitity(updatedTicket.getQuantity())
                .build();

        var response = ticketClient.updateTicket(request);
        return TicketDTO.builder()
                .id(response.getId())
                .eventName(response.getEventname())
                .eventPrice(response.getEventprice())
                .eventDate(response.getEventdate())
                .quantity(response.getQuanitity())
                .build();
    }



    public TicketOuterClass.Empty removeTicket(String id){
        var response = ticketClient.removeTicket(TicketOuterClass.TicketRequestId.newBuilder()
                .setId(id)
                .build());
        return response;
    }








}
