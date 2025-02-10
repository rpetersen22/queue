package org.rjpetersen.queue;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    private final DelayService delayService;
    private final MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository, DelayService delayService) {
        this.messageRepository = messageRepository;
        this.delayService = delayService;
    }

    public Optional<MessageEntity> poll() {
        Optional<MessageEntity> entity = messageRepository.peek();
        entity.ifPresent(messageRepository::delete);
        return entity;
    }

    public long getSize() {
        return messageRepository.count();
    }

    public boolean offer(String message) {
        //simulate message processing work
        try {
            delayService.blockingDelayRandom();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        messageRepository.save(new MessageEntity(message));
        return true;
    }



}
