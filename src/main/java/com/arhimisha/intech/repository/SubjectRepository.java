package com.arhimisha.intech.repository;

import com.arhimisha.intech.dto.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
}
