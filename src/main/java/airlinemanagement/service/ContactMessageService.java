package airlinemanagement.service;

import airlinemanagement.model.ContactMessage;
import airlinemanagement.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    public List<ContactMessage> getUnreadMessages() {
        return contactMessageRepository.findByIsReadFalse();
    }

    public List<ContactMessage> getReadMessages() {
        return contactMessageRepository.findByIsReadTrue();
    }

    public Optional<ContactMessage> getMessageById(Long id) {
        return contactMessageRepository.findById(id);
    }

    @Transactional
    public ContactMessage saveMessage(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    @Transactional
    public void markAsRead(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        message.setRead(true);
        contactMessageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }
}

