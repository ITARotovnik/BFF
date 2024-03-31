package com.example.BFF1.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TicketDTO {
    String id;
    String eventName;
    String eventDate;
    Double eventPrice;
    Integer quantity;

}
