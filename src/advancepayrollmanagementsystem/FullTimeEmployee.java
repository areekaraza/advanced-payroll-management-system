package advancepayrollmanagementsystem;

/**
 * FullTimeEmployee class representing a full-time employee with fixed salary and benefits.
 * Full-time employees receive a monthly salary regardless of hours worked, with overtime pay
 * for hours exceeding 160 per month.

 */
public class FullTimeEmployee extends Employee {
    private static final long serialVersionUID = 1L;
    
    private double monthlyBonus;
    private double benefits; // Health insurance, retirement contributions, etc.
    private int sickLeaveDays;
    private int vacationDays;
    
    /**
     * Constructor for FullTimeEmployee
     * @param employeeId Unique identifier
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param department Department
     * @param monthlySalary Monthly salary
     * @param benefits Monthly benefits amount
     */
    public FullTimeEmployee(String employeeId, String firstName, String lastName, 
                           String email, String department, double monthlySalary, double benefits) {
        super(employeeId, firstName, lastName, email, department, monthlySalary);
        this.benefits = benefits;
        this.monthlyBonus = 0;
        this.sickLeaveDays = 12; // 12 sick days per year
        this.vacationDays = 20;  // 20 vacation days per year
    }
    
    /**
     * Legacy constructor for backward compatibility
     */
    public FullTimeEmployee(String employeeId, String name, double hoursWorked, double hourlyRate) {
        super(employeeId, name, hoursWorked, hourlyRate);
        this.benefits = getBaseSalary() * 0.15; // 15% of salary as benefits
        this.monthlyBonus = 0;
        this.sickLeaveDays = 12;
        this.vacationDays = 20;
    }
    
    @Override
    public double calculateSalary() {
        double totalSalary = getBaseSalary() + monthlyBonus + benefits;
        
        // Add overtime pay if worked more than 160 hours (40 hours/week * 4 weeks)
        if (getHoursWorked() > 160) {
            double overtimeHours = getHoursWorked() - 160;
            double hourlyEquivalent = getBaseSalary() / 160;
            totalSalary += overtimeHours * hourlyEquivalent * 1.5; // 1.5x for overtime
        }
        
        return totalSalary;
    }
    
    @Override
    public double calculateTax() {
        double grossSalary = calculateSalary();
        double tax = 0;
        
        // Progressive tax calculation
        if (grossSalary <= 50000) {
            tax = grossSalary * 0.05; // 5% tax
        } else if (grossSalary <= 100000) {
            tax = 50000 * 0.05 + (grossSalary - 50000) * 0.10; // 5% + 10%
        } else {
            tax = 50000 * 0.05 + 50000 * 0.10 + (grossSalary - 100000) * 0.15; // 5% + 10% + 15%
        }
        
        return tax;
    }
    
    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
    
    /**
     * Calculate annual salary including all benefits and bonuses
     * @return annual salary
     */
    public double getAnnualSalary() {
        return calculateSalary() * 12;
    }
    
    // Getters and Setters
    public double getMonthlyBonus() { return monthlyBonus; }
    public void setMonthlyBonus(double monthlyBonus) { this.monthlyBonus = monthlyBonus; }
    
    public double getBenefits() { return benefits; }
    public void setBenefits(double benefits) { this.benefits = benefits; }
    
    public int getSickLeaveDays() { return sickLeaveDays; }
    public void setSickLeaveDays(int sickLeaveDays) { this.sickLeaveDays = sickLeaveDays; }
    
    public int getVacationDays() { return vacationDays; }
    public void setVacationDays(int vacationDays) { this.vacationDays = vacationDays; }
    
    @Override
    public String toString() {
        return String.format("FullTimeEmployee{ID='%s', Name='%s', Department='%s', MonthlySalary=%.2f, Benefits=%.2f}",
                getEmployeeId(), getFullName(), getDepartment(), getBaseSalary(), benefits);
    }
}
