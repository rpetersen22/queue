package org.rjpetersen.queue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository  extends CrudRepository<MessageEntity, Long> {
    @Query("SELECT e FROM MessageEntity e ORDER BY e.id ASC LIMIT 1")
    Optional<MessageEntity> peek();
}
