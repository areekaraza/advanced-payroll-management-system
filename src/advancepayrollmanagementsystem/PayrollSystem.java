package advancepayrollmanagementsystem;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Enhanced PayrollSystem class providing comprehensive payroll management functionality.
 * This class handles employee management, payroll calculations, reporting, and data persistence.
 * 
 * Features:
 * - Employee management (CRUD operations)
 * - Advanced payroll calculations with tax deductions
 * - Multiple employee types support
 * - Data persistence with file I/O
 * - Comprehensive reporting and analytics
 * - Search and filtering capabilities
 * - Data backup and restore
 */
public class PayrollSystem {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BOLD = "\u001B[1m";
    
    private static final String DATA_FILE = "payroll_data.ser";
    private static final String BACKUP_FILE = "payroll_backup.ser";
    private static final String EXPORT_DIR = "reports/";
    
    private ArrayList<Employee> employeeList;
    private Scanner input = new Scanner(System.in);
    
    /**
     * Constructor initializes the employee list and creates necessary directories
     */
    public PayrollSystem() {
        this.employeeList = new ArrayList<>();
        createDirectories();
    }
    
    /**
     * Create necessary directories for the system
     */
    private void createDirectories() {
        File reportsDir = new File(EXPORT_DIR);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
    }
    
    // Employee Management Methods
    
    /**
     * Add an employee to the system
     * @param employee Employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employeeExists(employee.getEmployeeId())) {
            employeeList.add(employee);
            System.out.println(ANSI_GREEN + "✓ Employee added successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "✗ Employee already exists or invalid data!" + ANSI_RESET);
        }
    }
    
    /**
     * Check if an employee exists in the system
     * @param employeeId Employee ID to check
     * @return true if employee exists, false otherwise
     */
    public boolean employeeExists(String employeeId) {
        return employeeList.stream()
                .anyMatch(emp -> emp.getEmployeeId().equalsIgnoreCase(employeeId));
    }
    
    /**
     * Find an employee by ID
     * @param employeeId Employee ID to search
     * @return Employee object if found, null otherwise
     */
    public Employee findEmployee(String employeeId) {
        return employeeList.stream()
                .filter(emp -> emp.getEmployeeId().equalsIgnoreCase(employeeId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Display all employees in a formatted table
     */
    public void displayAllEmployees() {
        if (employeeList.isEmpty()) {
            System.out.println(ANSI_YELLOW + "\nNo employees found in the system." + ANSI_RESET);
            return;
        }
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(120));
        System.out.println(ANSI_BOLD + "                                    ALL EMPLOYEES" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(120) + ANSI_RESET);
        
        // Table header
        System.out.printf(ANSI_BOLD + "%-10s %-20s %-15s %-20s %-12s %-15s %-20s%n" + ANSI_RESET,
                "ID", "Name", "Type", "Department", "Status", "Salary", "Email");
        System.out.println(ANSI_BLUE + "-".repeat(120) + ANSI_RESET);
        
        // Employee data
        for (Employee emp : employeeList) {
            String status = emp.isActive() ? ANSI_GREEN + "Active" + ANSI_WHITE : ANSI_RED + "Inactive" + ANSI_WHITE;
            System.out.printf(ANSI_WHITE + "%-10s %-20s %-15s %-20s %-20s $%-14.2f %-20s%n" + ANSI_RESET,
                    emp.getEmployeeId(),
                    emp.getFullName(),
                    emp.getEmployeeType(),
                    emp.getDepartment(),
                    status,
                    emp.calculateSalary(),
                    emp.getEmail());
        }
        
        System.out.println(ANSI_BLUE + "-".repeat(120) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Total Employees: " + employeeList.size() + ANSI_RESET);
    }
    
    /**
     * Search employees by different criteria
     * @param searchType 1=ID, 2=Name, 3=Department
     * @param searchTerm Search term
     */
    public void searchEmployees(int searchType, String searchTerm) {
        List<Employee> results = new ArrayList<>();
        
        switch (searchType) {
            case 1: // Search by ID
                Employee emp = findEmployee(searchTerm);
                if (emp != null) results.add(emp);
                break;
            case 2: // Search by Name
                results = employeeList.stream()
                        .filter(e -> e.getFullName().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case 3: // Search by Department
                results = employeeList.stream()
                        .filter(e -> e.getDepartment().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
                break;
        }
        
        if (results.isEmpty()) {
            System.out.println(ANSI_YELLOW + "\nNo employees found matching: " + searchTerm + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "\n✓ Found " + results.size() + " employee(s):" + ANSI_RESET);
            displayEmployeeList(results);
        }
    }
    
    /**
     * Update employee information
     * @param employeeId Employee ID to update
     */
    public void updateEmployee(String employeeId) {
        Employee emp = findEmployee(employeeId);
        if (emp == null) {
            System.out.println(ANSI_RED + "✗ Employee not found!" + ANSI_RESET);
            return;
        }
        
        System.out.println(ANSI_CYAN + "\nCurrent Employee Information:");
        displayEmployeeDetails(emp);
        
        System.out.println(ANSI_YELLOW + "\nWhat would you like to update?");
        System.out.println("1. Basic Information");
        System.out.println("2. Salary/Rate Information");
        System.out.println("3. Hours Worked");
        System.out.println("4. Department");
        System.out.println("5. Status (Active/Inactive)" + ANSI_RESET);
        
        System.out.print(ANSI_WHITE + "Choice (1-5): " + ANSI_RESET);
        int choice = input.nextInt();
        input.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                updateBasicInfo(emp);
                break;
            case 2:
                updateSalaryInfo(emp);
                break;
            case 3:
                System.out.print(ANSI_WHITE + "New Hours Worked: " + ANSI_RESET);
                double hours = input.nextDouble();
                emp.setHoursWorked(hours);
                break;
            case 4:
                System.out.print(ANSI_WHITE + "New Department: " + ANSI_RESET);
                String dept = input.nextLine();
                emp.setDepartment(dept);
                break;
            case 5:
                emp.setActive(!emp.isActive());
                System.out.println(ANSI_GREEN + "✓ Status changed to: " + 
                        (emp.isActive() ? "Active" : "Inactive") + ANSI_RESET);
                break;
            default:
                System.out.println(ANSI_RED + "Invalid choice!" + ANSI_RESET);
                return;
        }
        
        System.out.println(ANSI_GREEN + "✓ Employee updated successfully!" + ANSI_RESET);
    }
    
    /**
     * Update basic employee information
     */
    private void updateBasicInfo(Employee emp) {
        System.out.print(ANSI_WHITE + "New First Name (current: " + emp.getFirstName() + "): " + ANSI_RESET);
        String firstName = input.nextLine().trim();
        if (!firstName.isEmpty()) emp.setFirstName(firstName);
        
        System.out.print(ANSI_WHITE + "New Last Name (current: " + emp.getLastName() + "): " + ANSI_RESET);
        String lastName = input.nextLine().trim();
        if (!lastName.isEmpty()) emp.setLastName(lastName);
        
        System.out.print(ANSI_WHITE + "New Email (current: " + emp.getEmail() + "): " + ANSI_RESET);
        String email = input.nextLine().trim();
        if (!email.isEmpty()) emp.setEmail(email);
    }
    
    /**
     * Update salary/rate information
     */
    private void updateSalaryInfo(Employee emp) {
        if (emp instanceof FullTimeEmployee) {
            FullTimeEmployee ftEmp = (FullTimeEmployee) emp;
            System.out.print(ANSI_WHITE + "New Monthly Salary (current: " + emp.getBaseSalary() + "): " + ANSI_RESET);
            double salary = input.nextDouble();
            emp.setBaseSalary(salary);
            
            System.out.print(ANSI_WHITE + "New Benefits (current: " + ftEmp.getBenefits() + "): " + ANSI_RESET);
            double benefits = input.nextDouble();
            ftEmp.setBenefits(benefits);
        } else if (emp instanceof PartTimeEmployee) {
            System.out.print(ANSI_WHITE + "New Hourly Rate (current: " + emp.getHourlyRate() + "): " + ANSI_RESET);
            double rate = input.nextDouble();
            emp.setHourlyRate(rate);
        } else if (emp instanceof ContractEmployee) {
            ContractEmployee ctEmp = (ContractEmployee) emp;
            System.out.print(ANSI_WHITE + "New Contract Amount (current: " + ctEmp.getContractAmount() + "): " + ANSI_RESET);
            double amount = input.nextDouble();
            ctEmp.setContractAmount(amount);
        }
    }
    
    /**
     * Delete an employee from the system
     * @param employeeId Employee ID to delete
     */
    public void deleteEmployee(String employeeId) {
        Employee emp = findEmployee(employeeId);
        if (emp == null) {
            System.out.println(ANSI_RED + "✗ Employee not found!" + ANSI_RESET);
            return;
        }
        
        System.out.println(ANSI_YELLOW + "\nEmployee to delete:");
        displayEmployeeDetails(emp);
        
        System.out.print(ANSI_RED + "\nAre you sure you want to delete this employee? (yes/no): " + ANSI_RESET);
        String confirmation = input.nextLine().trim();
        
        if ("yes".equalsIgnoreCase(confirmation)) {
            employeeList.remove(emp);
            System.out.println(ANSI_GREEN + "✓ Employee deleted successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "Operation cancelled." + ANSI_RESET);
        }
    }
    
    // Payroll Calculation Methods
    
    /**
     * Calculate and display monthly salary for all employees
     */
    public void calculateMonthlySalary() {
        if (employeeList.isEmpty()) {
            System.out.println(ANSI_YELLOW + "\nNo employees found in the system." + ANSI_RESET);
            return;
        }
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(100));
        System.out.println(ANSI_BOLD + "                        MONTHLY PAYROLL REPORT" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(100) + ANSI_RESET);
        System.out.println(ANSI_WHITE + "Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(100) + ANSI_RESET);
        
        // Table header
        System.out.printf(ANSI_BOLD + "%-10s %-20s %-12s %-15s %-12s %-12s %-12s%n" + ANSI_RESET,
                "ID", "Name", "Type", "Gross Salary", "Tax", "Net Salary", "Status");
        System.out.println(ANSI_BLUE + "-".repeat(100) + ANSI_RESET);
        
        double totalGross = 0, totalTax = 0, totalNet = 0;
        int activeCount = 0;
        
        for (Employee emp : employeeList) {
            if (emp.isActive()) {
                double gross = emp.calculateSalary();
                double tax = emp.calculateTax();
                double net = emp.calculateNetSalary();
                
                totalGross += gross;
                totalTax += tax;
                totalNet += net;
                activeCount++;
                
                System.out.printf(ANSI_WHITE + "%-10s %-20s %-12s $%-14.2f $%-11.2f $%-11.2f %-12s%n" + ANSI_RESET,
                        emp.getEmployeeId(),
                        emp.getFullName().length() > 20 ? emp.getFullName().substring(0, 17) + "..." : emp.getFullName(),
                        emp.getEmployeeType(),
                        gross,
                        tax,
                        net,
                        "Active");
            }
        }
        
        System.out.println(ANSI_BLUE + "-".repeat(100) + ANSI_RESET);
        System.out.printf(ANSI_BOLD + ANSI_GREEN + "%-44s $%-14.2f $%-11.2f $%-11.2f%n" + ANSI_RESET,
                "TOTAL (" + activeCount + " employees):", totalGross, totalTax, totalNet);
        System.out.println(ANSI_CYAN + "═".repeat(100) + ANSI_RESET);
    }
    
    /**
     * Legacy method name for backward compatibility
     */
    public void calculatemonthlySalary() {
        calculateMonthlySalary();
    }
    
    /**
     * Display individual employee salary details
     * @param employeeId Employee ID
     */
    public void monthlySalaryIndividual(String employeeId) {
        Employee emp = findEmployee(employeeId);
        if (emp == null) {
            System.out.println(ANSI_RED + "✗ Employee not found!" + ANSI_RESET);
            return;
        }
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(80));
        System.out.println(ANSI_BOLD + "                    INDIVIDUAL SALARY REPORT" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(80) + ANSI_RESET);
        
        displayEmployeeDetails(emp);
        displaySalaryBreakdown(emp);
    }
    
    /**
     * Display detailed employee information
     */
    private void displayEmployeeDetails(Employee emp) {
        System.out.println(ANSI_WHITE + "Employee ID: " + ANSI_CYAN + emp.getEmployeeId());
        System.out.println(ANSI_WHITE + "Name: " + ANSI_CYAN + emp.getFullName());
        System.out.println(ANSI_WHITE + "Email: " + ANSI_CYAN + emp.getEmail());
        System.out.println(ANSI_WHITE + "Department: " + ANSI_CYAN + emp.getDepartment());
        System.out.println(ANSI_WHITE + "Employee Type: " + ANSI_CYAN + emp.getEmployeeType());
        System.out.println(ANSI_WHITE + "Status: " + (emp.isActive() ? ANSI_GREEN + "Active" : ANSI_RED + "Inactive"));
        System.out.println(ANSI_WHITE + "Date of Joining: " + ANSI_CYAN + emp.getFormattedDateOfJoining());
        System.out.println(ANSI_WHITE + "Years of Service: " + ANSI_CYAN + emp.getYearsOfService() + " years" + ANSI_RESET);
    }
    
    /**
     * Display salary breakdown for an employee
     */
    private void displaySalaryBreakdown(Employee emp) {
        System.out.println(ANSI_YELLOW + "\n--- SALARY BREAKDOWN ---" + ANSI_RESET);
        
        if (emp instanceof FullTimeEmployee) {
            FullTimeEmployee ftEmp = (FullTimeEmployee) emp;
            System.out.println(ANSI_WHITE + "Base Salary: " + ANSI_GREEN + "$" + String.format("%.2f", emp.getBaseSalary()));
            System.out.println(ANSI_WHITE + "Benefits: " + ANSI_GREEN + "$" + String.format("%.2f", ftEmp.getBenefits()));
            System.out.println(ANSI_WHITE + "Monthly Bonus: " + ANSI_GREEN + "$" + String.format("%.2f", ftEmp.getMonthlyBonus()));
        } else if (emp instanceof PartTimeEmployee) {
            System.out.println(ANSI_WHITE + "Hourly Rate: " + ANSI_GREEN + "$" + String.format("%.2f", emp.getHourlyRate()));
            System.out.println(ANSI_WHITE + "Hours Worked: " + ANSI_CYAN + String.format("%.1f", emp.getHoursWorked()));
            System.out.println(ANSI_WHITE + "Regular Pay: " + ANSI_GREEN + "$" + String.format("%.2f", emp.getHoursWorked() * emp.getHourlyRate()));
            if (emp.getHoursWorked() > 40) {
                System.out.println(ANSI_WHITE + "Overtime Pay: " + ANSI_GREEN + "$" + String.format("%.2f", emp.calculateOvertime()));
            }
        } else if (emp instanceof ContractEmployee) {
            ContractEmployee ctEmp = (ContractEmployee) emp;
            System.out.println(ANSI_WHITE + "Contract Amount: " + ANSI_GREEN + "$" + String.format("%.2f", ctEmp.getContractAmount()));
            System.out.println(ANSI_WHITE + "Project: " + ANSI_CYAN + ctEmp.getProjectName());
            System.out.println(ANSI_WHITE + "Contract End Date: " + ANSI_CYAN + ctEmp.getContractEndDate());
            System.out.println(ANSI_WHITE + "Days Remaining: " + ANSI_CYAN + ctEmp.getRemainingDays());
        }
        
        System.out.println(ANSI_BLUE + "-".repeat(40));
        System.out.println(ANSI_WHITE + "Gross Salary: " + ANSI_GREEN + "$" + String.format("%.2f", emp.calculateSalary()));
        System.out.println(ANSI_WHITE + "Tax Deduction: " + ANSI_RED + "$" + String.format("%.2f", emp.calculateTax()));
        System.out.println(ANSI_BLUE + "-".repeat(40));
        System.out.println(ANSI_BOLD + ANSI_WHITE + "Net Salary: " + ANSI_GREEN + "$" + String.format("%.2f", emp.calculateNetSalary()) + ANSI_RESET);
    }
    
    /**
     * Update hours worked for an employee
     */
    public void updateEmployeeHours(String employeeId, double hours) {
        Employee emp = findEmployee(employeeId);
        if (emp == null) {
            System.out.println(ANSI_RED + "✗ Employee not found!" + ANSI_RESET);
            return;
        }
        
        emp.setHoursWorked(hours);
        System.out.println(ANSI_GREEN + "✓ Hours updated successfully!" + ANSI_RESET);
        
        // Show updated salary
        System.out.println(ANSI_CYAN + "\nUpdated Salary Information:");
        displaySalaryBreakdown(emp);
    }
    
    /**
     * Process overtime for all eligible employees
     */
    public void processOvertime() {
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(80));
        System.out.println(ANSI_BOLD + "                    OVERTIME REPORT" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(80) + ANSI_RESET);
        
        boolean hasOvertime = false;
        
        for (Employee emp : employeeList) {
            if (emp.isActive() && emp.getHoursWorked() > 40) {
                if (!hasOvertime) {
                    System.out.printf(ANSI_BOLD + "%-15s %-20s %-12s %-15s %-15s%n" + ANSI_RESET,
                            "Employee ID", "Name", "Total Hours", "Overtime Hours", "Overtime Pay");
                    System.out.println(ANSI_BLUE + "-".repeat(80) + ANSI_RESET);
                    hasOvertime = true;
                }
                
                double overtimeHours = emp.getHoursWorked() - 40;
                double overtimePay = emp.calculateOvertime();
                
                System.out.printf(ANSI_WHITE + "%-15s %-20s %-12.1f %-15.1f $%-14.2f%n" + ANSI_RESET,
                        emp.getEmployeeId(),
                        emp.getFullName().length() > 20 ? emp.getFullName().substring(0, 17) + "..." : emp.getFullName(),
                        emp.getHoursWorked(),
                        overtimeHours,
                        overtimePay);
            }
        }
        
        if (!hasOvertime) {
            System.out.println(ANSI_YELLOW + "No employees with overtime hours found." + ANSI_RESET);
        }
    }
    
    // Reporting Methods
    
    /**
     * Generate department-wise report
     */
    public void generateDepartmentReport() {
        Map<String, List<Employee>> departmentMap = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.groupingBy(Employee::getDepartment));
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(80));
        System.out.println(ANSI_BOLD + "                    DEPARTMENT-WISE REPORT" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(80) + ANSI_RESET);
        
        for (Map.Entry<String, List<Employee>> entry : departmentMap.entrySet()) {
            String department = entry.getKey();
            List<Employee> employees = entry.getValue();
            
            double totalSalary = employees.stream()
                    .mapToDouble(Employee::calculateSalary)
                    .sum();
            
            System.out.println(ANSI_YELLOW + "\nDepartment: " + ANSI_BOLD + department + ANSI_RESET);
            System.out.println(ANSI_WHITE + "Employee Count: " + ANSI_CYAN + employees.size());
            System.out.println(ANSI_WHITE + "Total Salary Cost: " + ANSI_GREEN + "$" + String.format("%.2f", totalSalary));
            System.out.println(ANSI_WHITE + "Average Salary: " + ANSI_GREEN + "$" + String.format("%.2f", totalSalary / employees.size()) + ANSI_RESET);
            
            // List employees in this department
            employees.forEach(emp -> 
                System.out.println(ANSI_WHITE + "  • " + emp.getEmployeeId() + " - " + emp.getFullName() + 
                        " (" + emp.getEmployeeType() + ") - $" + String.format("%.2f", emp.calculateSalary()) + ANSI_RESET));
        }
    }
    
    /**
     * Generate employee type analysis report
     */
    public void generateEmployeeTypeReport() {
        Map<String, List<Employee>> typeMap = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.groupingBy(Employee::getEmployeeType));
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(80));
        System.out.println(ANSI_BOLD + "                    EMPLOYEE TYPE ANALYSIS" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(80) + ANSI_RESET);
        
        int totalEmployees = (int) employeeList.stream().filter(Employee::isActive).count();
        
        for (Map.Entry<String, List<Employee>> entry : typeMap.entrySet()) {
            String type = entry.getKey();
            List<Employee> employees = entry.getValue();
            
            double totalSalary = employees.stream()
                    .mapToDouble(Employee::calculateSalary)
                    .sum();
            
            double percentage = (employees.size() * 100.0) / totalEmployees;
            
            System.out.println(ANSI_YELLOW + "\nEmployee Type: " + ANSI_BOLD + type + ANSI_RESET);
            System.out.println(ANSI_WHITE + "Count: " + ANSI_CYAN + employees.size() + " (" + String.format("%.1f", percentage) + "%)");
            System.out.println(ANSI_WHITE + "Total Salary Cost: " + ANSI_GREEN + "$" + String.format("%.2f", totalSalary));
            System.out.println(ANSI_WHITE + "Average Salary: " + ANSI_GREEN + "$" + String.format("%.2f", totalSalary / employees.size()) + ANSI_RESET);
        }
    }
    
    /**
     * Generate salary statistics
     */
    public void generateSalaryStatistics() {
        List<Employee> activeEmployees = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());
        
        if (activeEmployees.isEmpty()) {
            System.out.println(ANSI_YELLOW + "No active employees found." + ANSI_RESET);
            return;
        }
        
        double totalSalary = activeEmployees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
        
        double avgSalary = totalSalary / activeEmployees.size();
        
        double maxSalary = activeEmployees.stream()
                .mapToDouble(Employee::calculateSalary)
                .max()
                .orElse(0);
        
        double minSalary = activeEmployees.stream()
                .mapToDouble(Employee::calculateSalary)
                .min()
                .orElse(0);
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(60));
        System.out.println(ANSI_BOLD + "                SALARY STATISTICS" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(60) + ANSI_RESET);
        
        System.out.println(ANSI_WHITE + "Total Employees: " + ANSI_CYAN + activeEmployees.size());
        System.out.println(ANSI_WHITE + "Total Salary Cost: " + ANSI_GREEN + "$" + String.format("%.2f", totalSalary));
        System.out.println(ANSI_WHITE + "Average Salary: " + ANSI_GREEN + "$" + String.format("%.2f", avgSalary));
        System.out.println(ANSI_WHITE + "Highest Salary: " + ANSI_GREEN + "$" + String.format("%.2f", maxSalary));
        System.out.println(ANSI_WHITE + "Lowest Salary: " + ANSI_GREEN + "$" + String.format("%.2f", minSalary) + ANSI_RESET);
        
        // Find employees with highest and lowest salaries
        Employee highestPaid = activeEmployees.stream()
                .max((e1, e2) -> Double.compare(e1.calculateSalary(), e2.calculateSalary()))
                .orElse(null);
        
        Employee lowestPaid = activeEmployees.stream()
                .min((e1, e2) -> Double.compare(e1.calculateSalary(), e2.calculateSalary()))
                .orElse(null);
        
        if (highestPaid != null) {
            System.out.println(ANSI_YELLOW + "\nHighest Paid: " + ANSI_WHITE + highestPaid.getFullName() + 
                    " (" + highestPaid.getEmployeeId() + ") - $" + String.format("%.2f", highestPaid.calculateSalary()) + ANSI_RESET);
        }
        
        if (lowestPaid != null) {
            System.out.println(ANSI_YELLOW + "Lowest Paid: " + ANSI_WHITE + lowestPaid.getFullName() + 
                    " (" + lowestPaid.getEmployeeId() + ") - $" + String.format("%.2f", lowestPaid.calculateSalary()) + ANSI_RESET);
        }
    }
    
    /**
     * Generate tax summary report
     */
    public void generateTaxReport() {
        List<Employee> activeEmployees = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());
        
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(80));
        System.out.println(ANSI_BOLD + "                    TAX SUMMARY REPORT" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(80) + ANSI_RESET);
        
        System.out.printf(ANSI_BOLD + "%-15s %-20s %-15s %-12s %-12s%n" + ANSI_RESET,
                "Employee ID", "Name", "Gross Salary", "Tax Amount", "Tax Rate");
        System.out.println(ANSI_BLUE + "-".repeat(80) + ANSI_RESET);
        
        double totalGrossSalary = 0;
        double totalTax = 0;
        
        for (Employee emp : activeEmployees) {
            double gross = emp.calculateSalary();
            double tax = emp.calculateTax();
            double taxRate = (tax / gross) * 100;
            
            totalGrossSalary += gross;
            totalTax += tax;
            
            System.out.printf(ANSI_WHITE + "%-15s %-20s $%-14.2f $%-11.2f %11.2f%%%n" + ANSI_RESET,
                    emp.getEmployeeId(),
                    emp.getFullName().length() > 20 ? emp.getFullName().substring(0, 17) + "..." : emp.getFullName(),
                    gross,
                    tax,
                    taxRate);
        }
        
        System.out.println(ANSI_BLUE + "-".repeat(80) + ANSI_RESET);
        double avgTaxRate = (totalTax / totalGrossSalary) * 100;
        System.out.printf(ANSI_BOLD + ANSI_GREEN + "%-37s $%-14.2f $%-11.2f %11.2f%%%n" + ANSI_RESET,
                "TOTAL:", totalGrossSalary, totalTax, avgTaxRate);
    }
    
    /**
     * Export all reports to files
     */
    public void exportReports() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            
            // Export employee list
            exportEmployeeList(EXPORT_DIR + "employees_" + timestamp + ".txt");
            
            // Export payroll report
            exportPayrollReport(EXPORT_DIR + "payroll_" + timestamp + ".txt");
            
            // Export statistics
            exportStatistics(EXPORT_DIR + "statistics_" + timestamp + ".txt");
            
            System.out.println(ANSI_GREEN + "✓ Reports exported successfully to " + EXPORT_DIR + ANSI_RESET);
            
        } catch (Exception e) {
            System.out.println(ANSI_RED + "✗ Error exporting reports: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    // Data Persistence Methods
    
    /**
     * Save employee data to file
     */
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employeeList);
            System.out.println(ANSI_GREEN + "✓ Data saved successfully!" + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "✗ Error saving data: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    /**
     * Load employee data from file
     */
    @SuppressWarnings("unchecked")
    public void loadData() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println(ANSI_YELLOW + "No existing data file found. Starting with empty system." + ANSI_RESET);
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            employeeList = (ArrayList<Employee>) ois.readObject();
            System.out.println(ANSI_GREEN + "✓ Data loaded successfully! (" + employeeList.size() + " employees)" + ANSI_RESET);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ANSI_RED + "✗ Error loading data: " + e.getMessage() + ANSI_RESET);
            employeeList = new ArrayList<>();
        }
    }
    
    /**
     * Backup current data
     */
    public void backupData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            oos.writeObject(employeeList);
            System.out.println(ANSI_GREEN + "✓ Data backed up successfully!" + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "✗ Error creating backup: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    /**
     * Restore data from backup
     */
    @SuppressWarnings("unchecked")
    public void restoreData() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println(ANSI_YELLOW + "No backup file found." + ANSI_RESET);
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            employeeList = (ArrayList<Employee>) ois.readObject();
            System.out.println(ANSI_GREEN + "✓ Data restored from backup successfully!" + ANSI_RESET);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ANSI_RED + "✗ Error restoring data: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    /**
     * Reset all data
     */
    public void resetAllData() {
        employeeList.clear();
        // Delete data files
        new File(DATA_FILE).delete();
        new File(BACKUP_FILE).delete();
    }
    
    // Utility Methods
    
    /**
     * Display a list of employees in table format
     */
    private void displayEmployeeList(List<Employee> employees) {
        System.out.printf(ANSI_BOLD + "%-10s %-20s %-15s %-20s %-15s%n" + ANSI_RESET,
                "ID", "Name", "Type", "Department", "Salary");
        System.out.println(ANSI_BLUE + "-".repeat(80) + ANSI_RESET);
        
        for (Employee emp : employees) {
            System.out.printf(ANSI_WHITE + "%-10s %-20s %-15s %-20s $%-14.2f%n" + ANSI_RESET,
                    emp.getEmployeeId(),
                    emp.getFullName(),
                    emp.getEmployeeType(),
                    emp.getDepartment(),
                    emp.calculateSalary());
        }
    }
    
    /**
     * Export employee list to text file
     */
    private void exportEmployeeList(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("EMPLOYEE LIST REPORT");
            writer.println("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.println("=".repeat(80));
            
            for (Employee emp : employeeList) {
                writer.println("ID: " + emp.getEmployeeId());
                writer.println("Name: " + emp.getFullName());
                writer.println("Type: " + emp.getEmployeeType());
                writer.println("Department: " + emp.getDepartment());
                writer.println("Email: " + emp.getEmail());
                writer.println("Status: " + (emp.isActive() ? "Active" : "Inactive"));
                writer.println("Salary: $" + String.format("%.2f", emp.calculateSalary()));
                writer.println("-".repeat(40));
            }
        }
    }
    
    /**
     * Export payroll report to text file
     */
    private void exportPayrollReport(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("MONTHLY PAYROLL REPORT");
            writer.println("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.println("=".repeat(80));
            
            double totalGross = 0, totalTax = 0, totalNet = 0;
            
            for (Employee emp : employeeList) {
                if (emp.isActive()) {
                    double gross = emp.calculateSalary();
                    double tax = emp.calculateTax();
                    double net = emp.calculateNetSalary();
                    
                    totalGross += gross;
                    totalTax += tax;
                    totalNet += net;
                    
                    writer.printf("%-15s %-25s $%-12.2f $%-10.2f $%-12.2f%n",
                            emp.getEmployeeId(), emp.getFullName(), gross, tax, net);
                }
            }
            
            writer.println("-".repeat(80));
            writer.printf("TOTAL: %-35s $%-12.2f $%-10.2f $%-12.2f%n",
                    "", totalGross, totalTax, totalNet);
        }
    }
    
    /**
     * Export statistics to text file
     */
    private void exportStatistics(String filename) throws IOException {
        List<Employee> activeEmployees = employeeList.stream()
                .filter(Employee::isActive)
                .collect(Collectors.toList());
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("PAYROLL STATISTICS REPORT");
            writer.println("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.println("=".repeat(60));
            
            if (!activeEmployees.isEmpty()) {
                double totalSalary = activeEmployees.stream()
                        .mapToDouble(Employee::calculateSalary)
                        .sum();
                
                writer.println("Total Employees: " + activeEmployees.size());
                writer.println("Total Salary Cost: $" + String.format("%.2f", totalSalary));
                writer.println("Average Salary: $" + String.format("%.2f", totalSalary / activeEmployees.size()));
                
                // Department breakdown
                Map<String, List<Employee>> departmentMap = activeEmployees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));
                
                writer.println("\nDEPARTMENT BREAKDOWN:");
                for (Map.Entry<String, List<Employee>> entry : departmentMap.entrySet()) {
                    writer.printf("%-20s: %d employees%n", entry.getKey(), entry.getValue().size());
                }
                
                // Employee type breakdown
                Map<String, List<Employee>> typeMap = activeEmployees.stream()
                        .collect(Collectors.groupingBy(Employee::getEmployeeType));
                
                writer.println("\nEMPLOYEE TYPE BREAKDOWN:");
                for (Map.Entry<String, List<Employee>> entry : typeMap.entrySet()) {
                    writer.printf("%-15s: %d employees%n", entry.getKey(), entry.getValue().size());
                }
            } else {
                writer.println("No active employees found.");
            }
        }
    }
    
    // Getter methods for system information
    
    public int getTotalEmployees() {
        return employeeList.size();
    }
    
    public int getActiveEmployees() {
        return (int) employeeList.stream().filter(Employee::isActive).count();
    }
}
