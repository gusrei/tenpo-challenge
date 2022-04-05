package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.domain.RequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestEntityRepository extends PagingAndSortingRepository<RequestEntity, Long> {

}
