package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends EntityWithSoftDeletingRepository<Subject, Long> {

    @Query( value = "select s.*, max(m.creation_date) as maxDate " +
                    "from subject as s " +
                    "left outer join message as m on s.id = m.subject_id and m.deleted = false " +
                    "where s.deleted = false " +
                    "Group By s.id " +
                    "order by maxDate desc nulls last ",
            countQuery = "select count(*) from subject where deleted = false",
            nativeQuery = true
    )
    Page<Subject> getAllSortedByMessagesCreationDate(Pageable pageable);

    long countAllByDeleted(boolean deleted);
}
