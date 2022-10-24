package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationIdUrl;
    private String compensationUrl;

    private Employee testEmployee;

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";

        testEmployee = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
    }


    @Test
    public void testCreateReadUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployee);
        testCompensation.setSalary("$100,000,000");
        testCompensation.setEffectiveDate(LocalDate.parse("2010-06-08"));

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getSalary());
//        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
//        assertEquals(createdCompensation.getEmployee(), readCompensation.getEmployee());
    }
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
