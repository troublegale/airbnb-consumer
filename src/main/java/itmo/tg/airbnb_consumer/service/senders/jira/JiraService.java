package itmo.tg.airbnb_consumer.service.senders.jira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.tg.airbnb_consumer.dto.GuestComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostDamageComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostJustificationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JiraService {

    private final JiraConnectionImpl jiraConnection;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public void sendIssue(GuestComplaintResponseDTO dto) {
        try {
            String description = mapToJSON(dto);
            String summary = "Guest Complaint #" + dto.getId();
            jiraConnection.createIssue(summary, description);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendIssue(HostDamageComplaintResponseDTO dto) {
        try {
            String description = mapToJSON(dto);
            String summary = "Host Damage Complaint #" + dto.getId();
            jiraConnection.createIssue(summary, description);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void sendIssue(HostJustificationResponseDTO dto) {
        try {
            String description = mapToJSON(dto);
            String summary = "Host Justification #" + dto.getId();
            jiraConnection.createIssue(summary, description);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private String mapToJSON(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}
