package com.tenpo.challenge.services.impl;

import com.tenpo.challenge.domain.repository.BlacklistedRepository;
import com.tenpo.challenge.services.BlacklistedTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {

    @Autowired
    BlacklistedRepository blacklistedTokenRepository;

    @Override
    public boolean existsToken(String jwt) {
        return !blacklistedTokenRepository.findByToken(jwt).isEmpty();
    }
}
