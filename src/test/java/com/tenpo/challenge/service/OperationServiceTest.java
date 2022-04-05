package com.tenpo.challenge.service;

import com.tenpo.challenge.services.OperationService;
import com.tenpo.challenge.services.impl.OperationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class OperationServiceTest {

    private OperationService operationService;

    @BeforeEach
    public void setup(){
        operationService = new OperationServiceImpl();
    }


    @Test
    public void add_operation_must_add_two_operand_ok(){

        Long addendA =Long.valueOf(4l);

        Long addendB = Long.valueOf(2l);

        Assertions.assertEquals(operationService.add(addendA, addendB), 6L);
    }
}
