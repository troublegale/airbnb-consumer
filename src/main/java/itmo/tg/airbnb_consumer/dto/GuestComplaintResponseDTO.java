package itmo.tg.airbnb_consumer.dto;

import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestComplaintResponseDTO {

    private Long id;

    private String guestEmail;

    private Long advertisementId;

    private Long bookingId;

    private String proofLink;

    private String date;

    private TicketStatus status;

    private String resolverEmail;

}

