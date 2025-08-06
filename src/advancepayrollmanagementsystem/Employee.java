package advancepayrollmanagementsystem;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract Employee class representing a generic employee in the payroll system.
 * This class serves as a base for different types of employees (FullTime, PartTime, Contract).
 * 
 * @author Your Name
 * @version 2.0
 */
public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Employee basic information
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private LocalDate dateOfJoining;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    
    // Salary related fields
    private double baseSalary;
    private double hoursWorked;
    private double hourlyRate;
    
    /**
     * Constructor for Employee
     * @param employeeId Unique identifier for the employee
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param email Employee's email address
     * @param department Department where employee works
     * @param baseSalary Base salary of the employee
     */
    public Employee(String employeeId, String firstName, String lastName, String email, 
                   String department, double baseSalary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.baseSalary = baseSalary;
        this.dateOfJoining = LocalDate.now();
        this.isActive = true;
        this.hoursWorked = 0;
        this.hourlyRate = 0;
    }
    
    /**
     * Legacy constructor for backward compatibility
     */
    public Employee(String employeeId, String name, double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        String[] nameParts = name.split(" ", 2);
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
        this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
        this.department = "General";
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.baseSalary = hourlyRate * 40 * 4; // Assuming 40 hours/week, 4 weeks/month
        this.dateOfJoining = LocalDate.now();
        this.isActive = true;
    }
    
    // Abstract methods to be implemented by subclasses
    public abstract double calculateSalary();
    public abstract double calculateTax();
    public abstract String getEmployeeType();
    
    /**
     * Calculate overtime salary based on hours worked
     * @return overtime amount
     */
    protected double calculateOvertime() {
        if (hoursWorked <= 40) {
            return 0;
        }
        return (hoursWorked - 40) * hourlyRate * 1.5; // 1.5x rate for overtime
    }
    
    /**
     * Calculate net salary after tax deductions
     * @return net salary
     */
    public double calculateNetSalary() {
        return calculateSalary() - calculateTax();
    }
    
    /**
     * Get full name of the employee
     * @return full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Get formatted date of joining
     * @return formatted date string
     */
    public String getFormattedDateOfJoining() {
        return dateOfJoining != null ? dateOfJoining.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
    }
    
    /**
     * Get years of service
     * @return years since joining
     */
    public long getYearsOfService() {
        return dateOfJoining != null ? 
            java.time.Period.between(dateOfJoining, LocalDate.now()).getYears() : 0;
    }
    
    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    
    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }
    
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    // Legacy getters for backward compatibility
    public String getEmployee_Id() { return employeeId; }
    public String getName() { return getFullName(); }
    public double getHours_worked() { return hoursWorked; }
    public double getHourly_rate() { return hourlyRate; }
    
    @Override
    public String toString() {
        return String.format("Employee{ID='%s', Name='%s', Department='%s', Type='%s', Active=%s}",
                employeeId, getFullName(), department, getEmployeeType(), isActive);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return employeeId.equals(employee.employeeId);
    }
    
    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }
}
