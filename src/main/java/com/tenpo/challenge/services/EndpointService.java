package com.tenpo.challenge.services;

import com.tenpo.challenge.domain.RequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface EndpointService {

    public Page<RequestEntity> findAll(Pageable pageable);

    public void save(RequestEntity endpoint);
}
