package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import com.mindex.challenge.data.ReportingStructure;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }


    public ReportingStructure getReportingStructure(String id) {
            LOG.debug("Calculating ReportingStructure for employeeId [{}]", id);
    
            // Retrieve the root employee (throws if not found)
            Employee employee = employeeRepository.findByEmployeeId(id);
            if (employee == null) {
                throw new RuntimeException("Invalid employeeId: " + id);
            }
    
            // Use a set to avoid double counting employees (protects against cycles)
            Set<String> visited = new HashSet<>();
    
            // Compute the total number of reports using DFS
            int numberOfReports = countReports(employee, visited);
    
            ReportingStructure rs = new ReportingStructure();
            rs.setEmployee(employee);
            rs.setNumberOfReports(numberOfReports);
            return rs;
        }

/**
 * Recursive helper to count all distinct reports under 'employee'.
 * visited keeps track of employeeIds already counted.
 */
private int countReports(Employee employee, Set<String> visited) {
    if (employee == null || employee.getDirectReports() == null) {
        return 0;
    }

    int total = 0;
    for (Employee reportRef : employee.getDirectReports()) {
        if (reportRef == null) {
            continue;
        }

        String reportId = reportRef.getEmployeeId();
        if (reportId == null) {
            // If the directReport object doesn't contain only an id, attempt to read its id field.
            // If still null, skip it.
            continue;
        }

        // If we've already counted this employee, skip to avoid double-counting / cycles
        if (visited.contains(reportId)) {
            continue;
        }

        // Mark visited and increment count for this direct report
        visited.add(reportId);
        total += 1;

        // Fetch the full Employee object from repository to walk its direct reports
        Employee fullReport = employeeRepository.findByEmployeeId(reportId);
        if (fullReport != null) {
            total += countReports(fullReport, visited);
        }
    }

    return total;
}
}

