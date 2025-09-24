package com.mindex.challenge.data;

/*
 * ReportingStructure represents an Employee together with the total number of reports
 * (direct + indirect) that report under that employee.
 * numberOfReports is computed at runtime and not persisted.
 */

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {}

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    @Override
    public String toString() {
        return "ReportingStructure{employee=" + (employee != null ? employee.getEmployeeId() : null) +
                ", numberOfReports=" + numberOfReports + "}";
    }
}
