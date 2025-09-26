package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CompensationService.
 *
 * Responsibilities:
 * - Validate the referenced employee exists when creating a Compensation (defensive check).
 * - Persist Compensation into MongoDB using CompensationRepository.
 * - Read Compensation by employeeId using a repository convenience method that looks up the embedded employee.employeeId.
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for employee [{}]", compensation != null && compensation.getEmployee() != null ? compensation.getEmployee().getEmployeeId() : null);

        if (compensation == null || compensation.getEmployee() == null || compensation.getEmployee().getEmployeeId() == null) {
            throw new RuntimeException("Compensation must include an employee with employeeId"); // simple validation
        }

        // Ensure the referenced employee exists in the database
        String empId = compensation.getEmployee().getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(empId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + empId);
        }

        // Persist the compensation. We save the Compensation document with the embedded Employee details.
        return compensationRepository.save(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation for employeeId [{}]", employeeId);

        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(employeeId);
        if (compensation == null) {
            throw new RuntimeException("No compensation found for employeeId: " + employeeId);
        }
        return compensation;
    }
}
