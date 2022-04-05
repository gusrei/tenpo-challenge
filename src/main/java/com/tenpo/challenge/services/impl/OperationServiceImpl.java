package com.tenpo.challenge.services.impl;

import com.tenpo.challenge.services.OperationService;
import org.springframework.stereotype.Service;

@Service("operationService")
public class OperationServiceImpl implements OperationService {
    @Override
    public Long add(Long addendA, Long addendB) {
        return addendA + addendB;
    }

    @Override
    public Long subtract(long minuend, Long subtracting) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float divide(Long dividend, Long divider) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long multiply(Long multiplying, Long multiplier) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }
}
