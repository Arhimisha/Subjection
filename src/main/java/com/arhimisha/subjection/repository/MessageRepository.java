package com.arhimisha.subjection.repository;

import com.arhimisha.subjection.domain.Message;
import com.arhimisha.subjection.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends EntityWithSoftDeletingRepository<Message, Long> {

    Page<Message> findAllBySubjectAndDeleted(Subject subject, boolean deleted, Pageable pageable);

    long countAllBySubjectAndDeleted(Subject subject, boolean deleted);
}
