package com.mindex.challenge.data;

import java.util.Objects;

/**
 * Compensation represents an employee's compensation details.
 * It associates an Employee (embedded) with a salary and an effective date.
 *
 * Design choices:
 * - We store an embedded Employee object so that compensation records clearly reference the employee
 *   and we can serialize the related employee into the Compensation document as stored in MongoDB.
 * - effectiveDate is stored as a String for simplicity (ISO-8601 like "YYYY-MM-DD"), but could be
 *   changed to java.time.LocalDate in a future improvement.
 * - salary is a double here for simplicity; in production BigDecimal is preferable to avoid precision issues.
 */
public class Compensation {
    private String id;
    private Employee employee;
    private double salary;
    private String effectiveDate;

    public Compensation() {}

    public Compensation(Employee employee, double salary, String effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "id='" + id + '\'' +
                ", employeeId='" + (employee != null ? employee.getEmployeeId() : null) + '\'' +
                ", salary=" + salary +
                ", effectiveDate='" + effectiveDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compensation)) return false;
        Compensation that = (Compensation) o;
        return Double.compare(that.salary, salary) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(employee != null ? employee.getEmployeeId() : null, that.employee != null ? that.employee.getEmployeeId() : null) &&
                Objects.equals(effectiveDate, that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee != null ? employee.getEmployeeId() : null, salary, effectiveDate);
    }
}
