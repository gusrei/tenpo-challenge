package com.tenpo.challenge.application;

import com.tenpo.challenge.domain.RequestEntity;
import com.tenpo.challenge.services.EndpointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final EndpointService endpointService;

    public HistoryController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @GetMapping()
    public Page<RequestEntity> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return endpointService.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
    }
}
