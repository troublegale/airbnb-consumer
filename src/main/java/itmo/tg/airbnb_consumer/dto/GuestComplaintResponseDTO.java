package itmo.tg.airbnb_consumer.dto;

import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestComplaintResponseDTO {

    private Long id;

    private String guestEmail;

    private Long advertisementId;

    private Long bookingId;

    private String proofLink;

    private LocalDate date;

    private TicketStatus status;

    private String resolverEmail;

}

