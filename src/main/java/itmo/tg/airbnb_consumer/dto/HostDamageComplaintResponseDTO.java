package itmo.tg.airbnb_consumer.dto;

import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import lombok.Data;

@Data
public class HostDamageComplaintResponseDTO {

    private Long id;

    private String hostEmail;

    private Long bookingId;

    private String proofLink;

    private Double compensationAmount;

    private TicketStatus status;

    private String resolverEmail;

}
