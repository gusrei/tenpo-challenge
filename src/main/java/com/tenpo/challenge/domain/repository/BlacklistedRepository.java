package com.tenpo.challenge.domain.repository;

import com.tenpo.challenge.infrastructure.redis.BlacklistedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistedRepository extends CrudRepository<BlacklistedToken, Long> {

    List<BlacklistedToken> findByToken(String token);

}

