package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing endpoints to create and read Compensation records.
 *
 * POST /compensation  - create a Compensation (expects a Compensation JSON containing an employee with employeeId)
 * GET  /compensation/{employeeId} - read the Compensation associated with the given employeeId
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received create compensation request [{}]", compensation);
        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/{employeeId}")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received read compensation request for employeeId [{}]", employeeId);
        return compensationService.read(employeeId);
    }
}
