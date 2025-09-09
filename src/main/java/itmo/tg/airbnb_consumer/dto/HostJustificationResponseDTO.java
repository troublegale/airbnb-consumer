package itmo.tg.airbnb_consumer.dto;

import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import lombok.Data;

@Data
public class HostJustificationResponseDTO {

    private Long id;

    private String hostEmail;

    private Long guestComplaintId;

    private String proofLink;

    private TicketStatus status;

    private String resolverEmail;

}
