package itmo.tg.airbnb_consumer.service;

import itmo.tg.airbnb_consumer.dto.FineDTO;
import itmo.tg.airbnb_consumer.dto.GuestComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostDamageComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostJustificationResponseDTO;
import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import itmo.tg.airbnb_consumer.service.senders.EmailService;
import itmo.tg.airbnb_consumer.service.senders.jira.JiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final JiraService jiraService;
    private final EmailService emailService;

    @KafkaListener(topics = "guest-complaints", groupId = "airbnb-group")
    public void listenGuestComplaints(GuestComplaintResponseDTO dto) {
        if (dto.getStatus() == TicketStatus.PENDING) {
            jiraService.sendIssue(dto);
        } else {
            emailService.sendEmail(dto);
        }
    }

    @KafkaListener(topics = "host-damage-complaints", groupId = "airbnb-group")
    public void listenHostDamageComplaints(HostDamageComplaintResponseDTO dto) {
        if (dto.getStatus() == TicketStatus.PENDING) {
            jiraService.sendIssue(dto);
        } else {
            emailService.sendEmail(dto);
        }
    }

    @KafkaListener(topics = "host-justifications", groupId = "airbnb-group")
    public void listenHostJustifications(HostJustificationResponseDTO dto) {
        if (dto.getStatus() == TicketStatus.PENDING) {
            jiraService.sendIssue(dto);
        } else {
            emailService.sendEmail(dto);
        }
    }

    @KafkaListener(topics = "fines", groupId = "airbnb-group")
    public void listenFines(FineDTO dto) {
        emailService.sendEmail(dto);
    }

}
