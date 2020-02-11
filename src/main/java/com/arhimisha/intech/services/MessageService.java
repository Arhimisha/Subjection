package com.arhimisha.intech.services;

import com.arhimisha.intech.domain.Message;
import com.arhimisha.intech.domain.Subject;
import com.arhimisha.intech.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    final private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return this.messageRepository.save(message);
    }

    public Optional<Message> loadById(long id) {
        return this.messageRepository.findById(id);
    }

    public Page<Message> loadAllBySubjectAndDeleted(Subject subject, boolean deleted, Pageable pageable) {
        return this.messageRepository.findAllBySubjectAndDeleted(subject, deleted, pageable);
    }

    public int softDelete(long id) {
        return this.messageRepository.softDeleteById(id);
    }

    public long countAllBySubjectAndDeleted(Subject subject, boolean deleted) {
        return this.messageRepository.countAllBySubjectAndDeleted(subject, deleted);
    }

}
