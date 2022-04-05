package com.tenpo.challenge.application;

import com.tenpo.challenge.services.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/add")
public class OperationsController {


    private final OperationService operationService;

    public OperationsController(OperationService operationService) {
        this.operationService = operationService;
    }


    @GetMapping()
    public Long add(@RequestParam Long a, @RequestParam Long b) {
        return operationService.add(a,b);
    }
}
