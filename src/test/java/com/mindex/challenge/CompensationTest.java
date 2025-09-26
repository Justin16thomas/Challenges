package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple integration test for Task 2 (Compensation).
 * This test creates a new Compensation object for an existing employee,
 * persists it via POST /compensation, then retrieves it back via GET /compensation/{id}
 * and verifies that the data round-trips correctly.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndReadCompensation() {
        // We'll assign compensation to John Lennon (already seeded employee)
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        String baseUrl = "http://localhost:" + port + "/compensation";

        // Build a compensation payload
        Compensation comp = new Compensation();
        Employee emp = new Employee();
        emp.setEmployeeId(employeeId);
        comp.setEmployee(emp);
        comp.setSalary(123456.78);
        comp.setEffectiveDate(java.time.LocalDate.now().toString());

        // --- Create Compensation ---
        Compensation created = restTemplate.postForObject(baseUrl, comp, Compensation.class);
        assertNotNull(created, "Created compensation should not be null");
        assertEquals(employeeId, created.getEmployee().getEmployeeId(), "EmployeeId should match");
        assertEquals(comp.getSalary(), created.getSalary(), "Salary should match input");
        assertEquals(comp.getEffectiveDate(), created.getEffectiveDate(), "EffectiveDate should match input");

        // --- Read Compensation ---
        Compensation fetched = restTemplate.getForObject(baseUrl + "/" + employeeId, Compensation.class);
        assertNotNull(fetched, "Fetched compensation should not be null");
        assertEquals(employeeId, fetched.getEmployee().getEmployeeId(), "EmployeeId should match");
        assertEquals(comp.getSalary(), fetched.getSalary(), "Salary should be consistent");
        assertEquals(comp.getEffectiveDate(), fetched.getEffectiveDate(), "EffectiveDate should be consistent");
    }
}
