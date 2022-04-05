package com.tenpo.challenge.services;

public interface BlacklistedTokenService {

    boolean existsToken(String jwt);

}
