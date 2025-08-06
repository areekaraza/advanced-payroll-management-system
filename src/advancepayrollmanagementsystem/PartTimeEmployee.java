package advancepayrollmanagementsystem;

/**
 * PartTimeEmployee class representing a part-time employee paid hourly.
 * Part-time employees are paid based on hours worked with overtime calculations.
 */
public class PartTimeEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    
    private double maxHoursPerWeek;
    private boolean eligibleForBenefits;
    
    /**
     * Constructor for PartTimeEmployee
     * @param employeeId Unique identifier
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param department Department
     * @param hourlyRate Hourly pay rate
     * @param maxHoursPerWeek Maximum hours allowed per week
     */
    public PartTimeEmployee(String employeeId, String firstName, String lastName, 
                           String email, String department, double hourlyRate, double maxHoursPerWeek) {
        super(employeeId, firstName, lastName, email, department, hourlyRate * 20 * 4); // Estimate based on 20 hrs/week
        setHourlyRate(hourlyRate);
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.eligibleForBenefits = maxHoursPerWeek >= 20; // Benefits if working 20+ hours/week
    }
    
    /**
     * Legacy constructor for backward compatibility
     */
    public PartTimeEmployee(String employeeId, String name, double hoursWorked, double hourlyRate) {
        super(employeeId, name, hoursWorked, hourlyRate);
        this.maxHoursPerWeek = 30; // Default max hours
        this.eligibleForBenefits = maxHoursPerWeek >= 20;
    }
    
    @Override
    public double calculateSalary() {
        double totalHours = getHoursWorked();
        double regularPay = 0;
        double overtimePay = 0;
        
        // Regular hours (up to 40 hours per week or maxHoursPerWeek * 4)
        double maxRegularHours = Math.min(40 * 4, maxHoursPerWeek * 4); // Monthly limit
        
        if (totalHours <= maxRegularHours) {
            regularPay = totalHours * getHourlyRate();
        } else {
            regularPay = maxRegularHours * getHourlyRate();
            overtimePay = (totalHours - maxRegularHours) * getHourlyRate() * 1.5;
        }
        
        return regularPay + overtimePay;
    }
    
    @Override
    public double calculateTax() {
        double grossSalary = calculateSalary();
        // Part-time employees typically have lower tax rates
        if (grossSalary <= 30000) {
            return grossSalary * 0.03; // 3% tax
        } else if (grossSalary <= 60000) {
            return 30000 * 0.03 + (grossSalary - 30000) * 0.08; // 3% + 8%
        } else {
            return 30000 * 0.03 + 30000 * 0.08 + (grossSalary - 60000) * 0.12; // 3% + 8% + 12%
        }
    }
    
    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
    
    /**
     * Check if employee is working within allowed hours
     * @return true if within limits, false otherwise
     */
    public boolean isWithinHourLimits() {
        return getHoursWorked() <= (maxHoursPerWeek * 4.33); // Monthly limit
    }
    
    // Getters and Setters
    public double getMaxHoursPerWeek() { return maxHoursPerWeek; }
    public void setMaxHoursPerWeek(double maxHoursPerWeek) { 
        this.maxHoursPerWeek = maxHoursPerWeek; 
        this.eligibleForBenefits = maxHoursPerWeek >= 20;
    }
    
    public boolean isEligibleForBenefits() { return eligibleForBenefits; }
    
    @Override
    public String toString() {
        return String.format("PartTimeEmployee{ID='%s', Name='%s', Department='%s', HourlyRate=%.2f, MaxHours/Week=%.1f}",
                getEmployeeId(), getFullName(), getDepartment(), getHourlyRate(), maxHoursPerWeek);
    }
}
