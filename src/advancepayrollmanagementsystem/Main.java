package advancepayrollmanagementsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Enhanced Payroll Management System
 * A comprehensive payroll management application supporting multiple employee types,
 * advanced features, and professional reporting capabilities.
 * 
 * Features:
 * - Multiple employee types (Full-time, Part-time, Contract)
 * - Advanced payroll calculations with tax deductions
 * - Employee management (add, update, search, delete)
 * - Data persistence (save/load from file)
 * - Professional reporting and analytics
 * - Input validation and error handling
 */
public class Main {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BOLD = "\u001B[1m";
    
    private static Scanner input = new Scanner(System.in);
    private static PayrollSystem payroll = new PayrollSystem();

    public static void main(String[] args) {
        System.out.println(ANSI_CYAN + ANSI_BOLD + 
            "╔══════════════════════════════════════════════════════════════╗\n" +
            "║            ADVANCED PAYROLL MANAGEMENT SYSTEM               ║\n" +
            "║                     Version 2.0                             ║\n" +
            "╚══════════════════════════════════════════════════════════════╝" + 
            ANSI_RESET);
        
        // Load existing data
        payroll.loadData();
        
        int choice;
        do {
            choice = showMainMenu();
            processMainMenuChoice(choice);
        } while (choice != 0);
        
        // Save data before exit
        payroll.saveData();
        input.close();
        
        System.out.println(ANSI_GREEN + "\n✓ Thank you for using Advanced Payroll Management System!" + ANSI_RESET);
    }
    
    /**
     * Display the main menu and get user choice
     */
    private static int showMainMenu() {
        System.out.println(ANSI_BLUE + "\n" + "═".repeat(60));
        System.out.println(ANSI_BOLD + "                    MAIN MENU" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "═".repeat(60) + ANSI_RESET);
        
        System.out.println(ANSI_WHITE + "1. " + ANSI_CYAN + "Employee Management");
        System.out.println(ANSI_WHITE + "2. " + ANSI_CYAN + "Payroll Operations");
        System.out.println(ANSI_WHITE + "3. " + ANSI_CYAN + "Reports & Analytics");
        System.out.println(ANSI_WHITE + "4. " + ANSI_CYAN + "System Settings");
        System.out.println(ANSI_WHITE + "0. " + ANSI_RED + "Exit System" + ANSI_RESET);
        
        return getValidChoice(0, 4, "\nSelect an option");
    }
    
    /**
     * Process the main menu choice
     */
    private static void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                employeeManagementMenu();
                break;
            case 2:
                payrollOperationsMenu();
                break;
            case 3:
                reportsMenu();
                break;
            case 4:
                systemSettingsMenu();
                break;
            case 0:
                System.out.println(ANSI_YELLOW + "\nSaving data and exiting..." + ANSI_RESET);
                break;
            default:
                System.out.println(ANSI_RED + "Invalid choice! Please try again." + ANSI_RESET);
        }
    }
    
    /**
     * Employee Management submenu
     */
    private static void employeeManagementMenu() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "\n" + "═".repeat(60));
            System.out.println(ANSI_BOLD + "                EMPLOYEE MANAGEMENT" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "═".repeat(60) + ANSI_RESET);
            
            System.out.println(ANSI_WHITE + "1. " + ANSI_GREEN + "Add New Employee");
            System.out.println(ANSI_WHITE + "2. " + ANSI_CYAN + "View All Employees");
            System.out.println(ANSI_WHITE + "3. " + ANSI_CYAN + "Search Employee");
            System.out.println(ANSI_WHITE + "4. " + ANSI_YELLOW + "Update Employee");
            System.out.println(ANSI_WHITE + "5. " + ANSI_RED + "Delete Employee");
            System.out.println(ANSI_WHITE + "0. " + ANSI_WHITE + "Back to Main Menu" + ANSI_RESET);
            
            choice = getValidChoice(0, 5, "\nSelect an option");
            
            switch (choice) {
                case 1:
                    addNewEmployee();
                    break;
                case 2:
                    payroll.displayAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
            }
        } while (choice != 0);
    }
    
    /**
     * Add a new employee with enhanced input and validation
     */
    private static void addNewEmployee() {
        System.out.println(ANSI_GREEN + "\n" + "═".repeat(60));
        System.out.println(ANSI_BOLD + "                ADD NEW EMPLOYEE" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "═".repeat(60) + ANSI_RESET);
        
        try {
            // Get employee type first
            System.out.println(ANSI_CYAN + "\nSelect Employee Type:");
            System.out.println("1. Full-Time Employee");
            System.out.println("2. Part-Time Employee");
            System.out.println("3. Contract Employee" + ANSI_RESET);
            
            int empType = getValidChoice(1, 3, "\nEmployee Type");
            
            // Common information
            System.out.print(ANSI_WHITE + "\nEmployee ID: " + ANSI_RESET);
            String employeeId = input.nextLine().trim();
            
            if (payroll.employeeExists(employeeId)) {
                System.out.println(ANSI_RED + "Error: Employee with ID " + employeeId + " already exists!" + ANSI_RESET);
                return;
            }
            
            System.out.print(ANSI_WHITE + "First Name: " + ANSI_RESET);
            String firstName = input.nextLine().trim();
            
            System.out.print(ANSI_WHITE + "Last Name: " + ANSI_RESET);
            String lastName = input.nextLine().trim();
            
            System.out.print(ANSI_WHITE + "Email: " + ANSI_RESET);
            String email = input.nextLine().trim();
            
            System.out.print(ANSI_WHITE + "Department: " + ANSI_RESET);
            String department = input.nextLine().trim();
            
            Employee employee = null;
            
            switch (empType) {
                case 1: // Full-Time
                    double monthlySalary = getValidDouble("Monthly Salary", 1000, 1000000);
                    double benefits = getValidDouble("Monthly Benefits", 0, 50000);
                    employee = new FullTimeEmployee(employeeId, firstName, lastName, email, department, monthlySalary, benefits);
                    break;
                    
                case 2: // Part-Time
                    double hourlyRate = getValidDouble("Hourly Rate", 10, 500);
                    double maxHours = getValidDouble("Max Hours per Week", 1, 40);
                    employee = new PartTimeEmployee(employeeId, firstName, lastName, email, department, hourlyRate, maxHours);
                    break;
                    
                case 3: // Contract
                    double contractAmount = getValidDouble("Contract Amount", 1000, 10000000);
                    System.out.print(ANSI_WHITE + "Contract End Date (YYYY-MM-DD): " + ANSI_RESET);
                    LocalDate endDate = parseDate(input.nextLine().trim());
                    System.out.print(ANSI_WHITE + "Project Name: " + ANSI_RESET);
                    String projectName = input.nextLine().trim();
                    employee = new ContractEmployee(employeeId, firstName, lastName, email, department, contractAmount, endDate, projectName);
                    break;
            }
            
            if (employee != null) {
                payroll.addEmployee(employee);
                System.out.println(ANSI_GREEN + "\n✓ Employee added successfully!" + ANSI_RESET);
                System.out.println(employee);
            }
            
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error adding employee: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    /**
     * Legacy method for backward compatibility - creates a PartTimeEmployee
     */
    private static void addLegacyEmployee() {
        System.out.print("Please Enter Employee ID: ");
        String employeeId = input.nextLine();

        System.out.print("Please Enter Employee Name: ");
        String name = input.nextLine();

        double hoursWorked = getValidDouble("Hours Worked", 0, 168);
        double hourlyRate = getValidDouble("Hourly Rate", 0, 1000);

        Employee employee = new PartTimeEmployee(employeeId, name, hoursWorked, hourlyRate);
        payroll.addEmployee(employee);
        System.out.println("Employee added successfully!");
    }
    
    /**
     * Search for employees
     */
    private static void searchEmployee() {
        System.out.println(ANSI_CYAN + "\nSearch Options:");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department" + ANSI_RESET);
        
        int searchType = getValidChoice(1, 3, "\nSearch Type");
        System.out.print(ANSI_WHITE + "Enter search term: " + ANSI_RESET);
        String searchTerm = input.nextLine().trim();
        
        payroll.searchEmployees(searchType, searchTerm);
    }
    
    /**
     * Update employee information
     */
    private static void updateEmployee() {
        System.out.print(ANSI_WHITE + "\nEnter Employee ID to update: " + ANSI_RESET);
        String empId = input.nextLine().trim();
        payroll.updateEmployee(empId);
    }
    
    /**
     * Delete employee
     */
    private static void deleteEmployee() {
        System.out.print(ANSI_WHITE + "\nEnter Employee ID to delete: " + ANSI_RESET);
        String empId = input.nextLine().trim();
        payroll.deleteEmployee(empId);
    }
    
    /**
     * Payroll Operations submenu
     */
    private static void payrollOperationsMenu() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "\n" + "═".repeat(60));
            System.out.println(ANSI_BOLD + "                PAYROLL OPERATIONS" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "═".repeat(60) + ANSI_RESET);
            
            System.out.println(ANSI_WHITE + "1. " + ANSI_CYAN + "Calculate Monthly Payroll");
            System.out.println(ANSI_WHITE + "2. " + ANSI_CYAN + "Individual Employee Salary");
            System.out.println(ANSI_WHITE + "3. " + ANSI_CYAN + "Update Hours Worked");
            System.out.println(ANSI_WHITE + "4. " + ANSI_CYAN + "Process Overtime");
            System.out.println(ANSI_WHITE + "0. " + ANSI_WHITE + "Back to Main Menu" + ANSI_RESET);
            
            choice = getValidChoice(0, 4, "\nSelect an option");
            
            switch (choice) {
                case 1:
                    payroll.calculateMonthlySalary();
                    break;
                case 2:
                    System.out.print(ANSI_WHITE + "\nEnter Employee ID: " + ANSI_RESET);
                    String empId = input.nextLine().trim();
                    payroll.monthlySalaryIndividual(empId);
                    break;
                case 3:
                    updateHoursWorked();
                    break;
                case 4:
                    payroll.processOvertime();
                    break;
            }
        } while (choice != 0);
    }
    
    /**
     * Update hours worked for an employee
     */
    private static void updateHoursWorked() {
        System.out.print(ANSI_WHITE + "\nEnter Employee ID: " + ANSI_RESET);
        String empId = input.nextLine().trim();
        double hours = getValidDouble("Hours Worked", 0, 200);
        payroll.updateEmployeeHours(empId, hours);
    }
    
    /**
     * Reports and Analytics submenu
     */
    private static void reportsMenu() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "\n" + "═".repeat(60));
            System.out.println(ANSI_BOLD + "                REPORTS & ANALYTICS" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "═".repeat(60) + ANSI_RESET);
            
            System.out.println(ANSI_WHITE + "1. " + ANSI_CYAN + "Department-wise Report");
            System.out.println(ANSI_WHITE + "2. " + ANSI_CYAN + "Employee Type Analysis");
            System.out.println(ANSI_WHITE + "3. " + ANSI_CYAN + "Salary Statistics");
            System.out.println(ANSI_WHITE + "4. " + ANSI_CYAN + "Tax Summary Report");
            System.out.println(ANSI_WHITE + "5. " + ANSI_CYAN + "Export Reports");
            System.out.println(ANSI_WHITE + "0. " + ANSI_WHITE + "Back to Main Menu" + ANSI_RESET);
            
            choice = getValidChoice(0, 5, "\nSelect an option");
            
            switch (choice) {
                case 1:
                    payroll.generateDepartmentReport();
                    break;
                case 2:
                    payroll.generateEmployeeTypeReport();
                    break;
                case 3:
                    payroll.generateSalaryStatistics();
                    break;
                case 4:
                    payroll.generateTaxReport();
                    break;
                case 5:
                    payroll.exportReports();
                    break;
            }
        } while (choice != 0);
    }
    
    /**
     * System Settings submenu
     */
    private static void systemSettingsMenu() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "\n" + "═".repeat(60));
            System.out.println(ANSI_BOLD + "                SYSTEM SETTINGS" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "═".repeat(60) + ANSI_RESET);
            
            System.out.println(ANSI_WHITE + "1. " + ANSI_CYAN + "Backup Data");
            System.out.println(ANSI_WHITE + "2. " + ANSI_CYAN + "Restore Data");
            System.out.println(ANSI_WHITE + "3. " + ANSI_CYAN + "System Information");
            System.out.println(ANSI_WHITE + "4. " + ANSI_YELLOW + "Reset All Data");
            System.out.println(ANSI_WHITE + "0. " + ANSI_WHITE + "Back to Main Menu" + ANSI_RESET);
            
            choice = getValidChoice(0, 4, "\nSelect an option");
            
            switch (choice) {
                case 1:
                    payroll.backupData();
                    break;
                case 2:
                    payroll.restoreData();
                    break;
                case 3:
                    showSystemInfo();
                    break;
                case 4:
                    resetAllData();
                    break;
            }
        } while (choice != 0);
    }
    
    /**
     * Show system information
     */
    private static void showSystemInfo() {
        System.out.println(ANSI_CYAN + "\n" + "═".repeat(60));
        System.out.println(ANSI_BOLD + "                SYSTEM INFORMATION" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "═".repeat(60) + ANSI_RESET);
        
        System.out.println(ANSI_WHITE + "System Name: " + ANSI_CYAN + "Advanced Payroll Management System");
        System.out.println(ANSI_WHITE + "Version: " + ANSI_CYAN + "2.0");
        System.out.println(ANSI_WHITE + "Total Employees: " + ANSI_CYAN + payroll.getTotalEmployees());
        System.out.println(ANSI_WHITE + "Active Employees: " + ANSI_CYAN + payroll.getActiveEmployees());
        System.out.println(ANSI_WHITE + "Current Date: " + ANSI_CYAN + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println(ANSI_WHITE + "Java Version: " + ANSI_CYAN + System.getProperty("java.version") + ANSI_RESET);
    }
    
    /**
     * Reset all data with confirmation
     */
    private static void resetAllData() {
        System.out.println(ANSI_RED + "\n⚠️  WARNING: This will delete ALL employee data!" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "Type 'CONFIRM' to proceed: " + ANSI_RESET);
        String confirmation = input.nextLine().trim();
        
        if ("CONFIRM".equals(confirmation)) {
            payroll.resetAllData();
            System.out.println(ANSI_GREEN + "✓ All data has been reset." + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "Operation cancelled." + ANSI_RESET);
        }
    }
    
    // Utility Methods
    
    /**
     * Get valid integer choice within range
     */
    private static int getValidChoice(int min, int max, String prompt) {
        int choice;
        boolean valid;
        
        do {
            try {
                System.out.print(ANSI_WHITE + prompt + " (" + min + "-" + max + "): " + ANSI_RESET);
                choice = input.nextInt();
                input.nextLine(); // consume newline
                valid = choice >= min && choice <= max;
                
                if (!valid) {
                    System.out.println(ANSI_RED + "Invalid choice! Please enter a number between " + min + " and " + max + ANSI_RESET);
                }
            } catch (Exception e) {
                input.nextLine(); // consume invalid input
                System.out.println(ANSI_RED + "Invalid input! Please enter a valid number." + ANSI_RESET);
                choice = -1;
                valid = false;
            }
        } while (!valid);
        
        return choice;
    }
    
    /**
     * Get valid double value within range
     */
    private static double getValidDouble(String fieldName, double min, double max) {
        double value;
        boolean valid;
        
        do {
            try {
                System.out.print(ANSI_WHITE + fieldName + " (Min: " + min + ", Max: " + max + "): " + ANSI_RESET);
                value = input.nextDouble();
                input.nextLine(); // consume newline
                valid = value >= min && value <= max;
                
                if (!valid) {
                    System.out.println(ANSI_RED + "Invalid value! Please enter a number between " + min + " and " + max + ANSI_RESET);
                }
            } catch (Exception e) {
                input.nextLine(); // consume invalid input
                System.out.println(ANSI_RED + "Invalid input! Please enter a valid number." + ANSI_RESET);
                value = -1;
                valid = false;
            }
        } while (!valid);
        
        return value;
    }
    
    /**
     * Parse date string to LocalDate
     */
    private static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            System.out.println(ANSI_YELLOW + "Invalid date format. Using default date (6 months from now)." + ANSI_RESET);
            return LocalDate.now().plusMonths(6);
        }
    }
}
