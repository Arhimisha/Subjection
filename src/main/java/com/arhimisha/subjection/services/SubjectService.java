package com.arhimisha.subjection.services;

import com.arhimisha.subjection.domain.Subject;
import com.arhimisha.subjection.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {
    final private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Optional<Subject> loadById(long id) {
        return subjectRepository.findById(id);

    }

    public int softDelete(long id){
        return this.subjectRepository.softDeleteById(id);
    }

    public Page<Subject> getAllSortedByMessagesCreationDate(Pageable pageable){
        return this.subjectRepository.getAllSortedByMessagesCreationDate(pageable);
    }

    public long countAllByDeleted(boolean deleted){
        return this.subjectRepository.countAllByDeleted(deleted);
    }
}
