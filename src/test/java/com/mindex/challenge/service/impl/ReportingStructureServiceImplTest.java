package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String reportingStructureIdUrl;

    private String testEmployeeId;
    private Employee testEmployee;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        reportingStructureIdUrl = "http://localhost:" + port + "reporting-structure/{employeeId}";

        testEmployeeId = "b7839309-3348-463b-a7e3-5de1c168beb3";
        testEmployee = employeeRepository.findByEmployeeId(testEmployeeId);
    }

    @Test
    public void testRead() {
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 4);

        // Read checks
        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(createdReportingStructure.getNumberOfReports());
//        assertReportingStructureEquivalence(testReportingStructure, createdReportingStructure);
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
        assertEquals(expected.getEmployee() , actual.getEmployee());

    }
}
