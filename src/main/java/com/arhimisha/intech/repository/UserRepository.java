package com.arhimisha.intech.repository;

import com.arhimisha.intech.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    public User findByUsername(String username);

    public User findByEmail (String email);
}
