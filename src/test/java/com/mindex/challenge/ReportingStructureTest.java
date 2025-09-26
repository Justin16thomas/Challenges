package com.mindex.challenge;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

/**
 * Simple integration test for Task 1 (ReportingStructure).
 * This test starts the Spring Boot application with a random port,
 * calls the /reportingStructure/{id} endpoint for John Lennon, and
 * verifies that the expected number of reports (4) is returned.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testReportingStructureForJohnLennon() {
        // John Lennon is seeded into the bootstrap data with this employeeId
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        String url = "http://localhost:" + port + "/reportingStructure/" + employeeId;

        ReportingStructure rs = restTemplate.getForObject(url, ReportingStructure.class);
        assertNotNull(rs, "ReportingStructure response should not be null");
        assertNotNull(rs.getEmployee(), "Employee field should not be null");
        assertEquals(employeeId, rs.getEmployee().getEmployeeId(), "Employee ID should match request");
        // Verify recursive counting of reports works correctly
        assertEquals(4, rs.getNumberOfReports(), "John Lennon should have 4 total reports");
    }
}
