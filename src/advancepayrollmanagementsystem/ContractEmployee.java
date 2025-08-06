package advancepayrollmanagementsystem;

/**
 * ContractEmployee class representing a contract-based employee.
 * Contract employees work on specific projects with predetermined rates and durations.
 * 
 *  
 */
public class ContractEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    
    private double contractAmount;
    private java.time.LocalDate contractStartDate;
    private java.time.LocalDate contractEndDate;
    private String projectName;
    private boolean isProjectCompleted;
    
    /**
     * Constructor for ContractEmployee
     * @param employeeId Unique identifier
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param department Department
     * @param contractAmount Total contract amount
     * @param contractEndDate Contract end date
     * @param projectName Project name
     */
    public ContractEmployee(String employeeId, String firstName, String lastName, 
                           String email, String department, double contractAmount,
                           java.time.LocalDate contractEndDate, String projectName) {
        super(employeeId, firstName, lastName, email, department, contractAmount);
        this.contractAmount = contractAmount;
        this.contractStartDate = java.time.LocalDate.now();
        this.contractEndDate = contractEndDate;
        this.projectName = projectName;
        this.isProjectCompleted = false;
    }
    
    /**
     * Legacy constructor for backward compatibility
     */
    public ContractEmployee(String employeeId, String name, double hoursWorked, double hourlyRate) {
        super(employeeId, name, hoursWorked, hourlyRate);
        this.contractAmount = hoursWorked * hourlyRate;
        this.contractStartDate = java.time.LocalDate.now();
        this.contractEndDate = java.time.LocalDate.now().plusMonths(6); // 6 month default contract
        this.projectName = "General Contract Work";
        this.isProjectCompleted = false;
    }
    
    @Override
    public double calculateSalary() {
        if (isProjectCompleted) {
            return contractAmount;
        }
        
        // Calculate pro-rated payment based on time elapsed
        long totalDays = java.time.Period.between(contractStartDate, contractEndDate).getDays();
        long daysElapsed = java.time.Period.between(contractStartDate, java.time.LocalDate.now()).getDays();
        
        if (totalDays <= 0) return contractAmount;
        
        double progressRatio = Math.min(1.0, (double) daysElapsed / totalDays);
        return contractAmount * progressRatio;
    }
    
    @Override
    public double calculateTax() {
        double grossSalary = calculateSalary();
        // Contract employees typically handle their own taxes, but we'll calculate estimated tax
        return grossSalary * 0.20; // Flat 20% for contract work
    }
    
    @Override
    public String getEmployeeType() {
        return "Contract";
    }
    
    /**
     * Mark the project as completed
     */
    public void completeProject() {
        this.isProjectCompleted = true;
    }
    
    /**
     * Check if contract has expired
     * @return true if contract has expired
     */
    public boolean isContractExpired() {
        return java.time.LocalDate.now().isAfter(contractEndDate);
    }
    
    /**
     * Get remaining days in contract
     * @return days remaining, or 0 if expired
     */
    public long getRemainingDays() {
        if (isContractExpired()) return 0;
        return java.time.Period.between(java.time.LocalDate.now(), contractEndDate).getDays();
    }
    
    // Getters and Setters
    public double getContractAmount() { return contractAmount; }
    public void setContractAmount(double contractAmount) { this.contractAmount = contractAmount; }
    
    public java.time.LocalDate getContractStartDate() { return contractStartDate; }
    public void setContractStartDate(java.time.LocalDate contractStartDate) { this.contractStartDate = contractStartDate; }
    
    public java.time.LocalDate getContractEndDate() { return contractEndDate; }
    public void setContractEndDate(java.time.LocalDate contractEndDate) { this.contractEndDate = contractEndDate; }
    
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    
    public boolean isProjectCompleted() { return isProjectCompleted; }
    public void setProjectCompleted(boolean projectCompleted) { isProjectCompleted = projectCompleted; }
    
    @Override
    public String toString() {
        return String.format("ContractEmployee{ID='%s', Name='%s', Project='%s', Amount=%.2f, Expires=%s}",
                getEmployeeId(), getFullName(), projectName, contractAmount, contractEndDate);
    }
}
