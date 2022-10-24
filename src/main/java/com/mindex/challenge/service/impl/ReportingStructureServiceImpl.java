package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService  {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading reporting structure with id: [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        int totalReports = this.getNumberOfReports(id);

        return new ReportingStructure(employee, totalReports);
    }

    private int getNumberOfReports(String id) {
        int totalReports = 0;
        Employee employee = employeeRepository.findByEmployeeId(id);

        List<Employee> reports = employee.getDirectReports();

        // Recursion until reaching employee with no reports
        if (reports != null) {
            for (Employee reportingEmployee : reports) {
                totalReports += 1 + getNumberOfReports(reportingEmployee.getEmployeeId());
            }
        }

        return totalReports;
    }
}
