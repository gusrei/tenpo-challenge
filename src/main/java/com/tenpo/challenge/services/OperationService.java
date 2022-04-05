package com.tenpo.challenge.services;

import jdk.jshell.spi.ExecutionControl;

public interface OperationService {

    Long add(Long addendA, Long addendB);

    Long subtract(long minuend, Long subtracting);

    Float divide(Long dividend, Long divider);

    Long multiply(Long multiplying, Long multiplier) throws ExecutionControl.NotImplementedException;
}
