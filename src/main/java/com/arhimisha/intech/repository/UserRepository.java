package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail (String email);
}
