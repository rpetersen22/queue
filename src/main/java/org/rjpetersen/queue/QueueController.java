package org.rjpetersen.queue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1")
public class QueueController {

    private final MessageService messageService;
    public QueueController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/dequeue")
    public ResponseEntity<MessageEntity> dequeue() {
        var entity = messageService.poll();
        return entity.map(messageEntity -> new ResponseEntity<>(messageEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/queue-size")
    public ResponseEntity<Long> queueSize() {
        return new ResponseEntity<>(messageService.getSize(), HttpStatus.OK);
    }

    @PostMapping("/enqueue")
    public ResponseEntity<Void> enqueue(@RequestBody MessageDTO dto) {
        boolean isCreated = messageService.offer(dto.message());
        return isCreated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
