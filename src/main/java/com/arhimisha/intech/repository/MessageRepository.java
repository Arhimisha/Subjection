package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.Message;
import com.arhimisha.intech.domain.Subject;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    List<Message> findAllBySubjectAndDeleted(Subject subject, boolean deleted, Sort sort);
}
