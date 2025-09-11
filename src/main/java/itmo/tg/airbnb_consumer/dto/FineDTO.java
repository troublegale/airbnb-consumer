package itmo.tg.airbnb_consumer.dto;

import itmo.tg.airbnb_consumer.dto.enums.FineStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FineDTO {

    private Long id;

    private Double amount;

    private FineStatus status;

    private String email;

}
