package com.tenpo.challenge.services.impl;

import com.tenpo.challenge.domain.RequestEntity;
import com.tenpo.challenge.domain.repository.RequestEntityRepository;
import com.tenpo.challenge.services.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private RequestEntityRepository endpointEntryRepository;

    public Page<RequestEntity> findAll(Pageable pageable){
        return endpointEntryRepository.findAll(pageable);
    }

    public void save(RequestEntity endpoint) {
        endpointEntryRepository.save(endpoint);
    }
}
