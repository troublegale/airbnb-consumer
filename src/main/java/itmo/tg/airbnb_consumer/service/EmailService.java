package itmo.tg.airbnb_consumer.service;

import itmo.tg.airbnb_consumer.dto.GuestComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostDamageComplaintResponseDTO;
import itmo.tg.airbnb_consumer.dto.HostJustificationResponseDTO;
import itmo.tg.airbnb_consumer.dto.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(GuestComplaintResponseDTO dto) {
        String address = dto.getGuestEmail();
        String title = "Guest Complaint was reviewed";
        String text = String.format(
                "Your Complaint #%d has been reviewed by an administrator.\n%s",
                dto.getId(),
                dto.getStatus() == TicketStatus.APPROVED
                        ? "It was approved.\nPenalties were applied to the owner of Advertisement #" + dto.getAdvertisementId()
                        : "It was rejected."
        );
        doSendEmail(address, title, text);
    }

    public void sendEmail(HostDamageComplaintResponseDTO dto) {
        String address = dto.getHostEmail();
        String title = "Host Damage Complaint was reviewed";
        String text = String.format(
                "Your Complaint #%d has been reviewed by an administrator.\n%s",
                dto.getId(),
                dto.getStatus() == TicketStatus.APPROVED
                        ? "It was approved.\nGuest of Booking #" + dto.getBookingId() + " was charged with " + dto.getCompensationAmount() + "."
                        : "It was rejected."
        );
        doSendEmail(address, title, text);
    }

    public void sendEmail(HostJustificationResponseDTO dto) {
        String address = dto.getHostEmail();
        String title = "Host Justification was reviewed";
        String text = String.format(
                "Your Justification #%d has been reviewed by an administrator.\n%s",
                dto.getId(),
                dto.getStatus() == TicketStatus.APPROVED
                        ? "It was approved.\nPenalties cause by Guest Complaint #" + dto.getGuestComplaintId() + " were retracted."
                        : "It was rejected."
        );
        doSendEmail(address, title, text);
    }

    private void doSendEmail(String address, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(title);
        message.setText(text);
        new Thread(() -> mailSender.send(message)).start();
    }

}
