package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.Message;
import com.arhimisha.intech.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends EntityWithSoftDeletingRepository<Message, Long> {

    Page<Message> findAllBySubjectAndDeleted(Subject subject, boolean deleted, Pageable pageable);
}
