package com.arhimisha.subjection.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Repository Interface for entity with field "deleted"
 */
@NoRepositoryBean
public interface EntityWithSoftDeletingRepository<T, E> extends PagingAndSortingRepository<T, E> {

    @Modifying
    @Query("update #{#entityName} set deleted = true where id=?1")
    int softDeleteById(long id);
}
