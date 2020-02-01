package com.arhimisha.intech.services;

import com.arhimisha.intech.domain.Message;
import com.arhimisha.intech.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Message save(Message message){
        return this.messageRepository.save(message);
    }
    public Optional<Message> loadById(long id){
        return this.messageRepository.findById(id);
    }

}
