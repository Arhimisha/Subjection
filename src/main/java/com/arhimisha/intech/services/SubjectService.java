package com.arhimisha.intech.services;

import com.arhimisha.intech.domain.Subject;
import com.arhimisha.intech.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
