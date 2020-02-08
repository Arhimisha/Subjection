package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.Subject;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends EntityWithSoftDeletingRepository<Subject, Long> {
}
