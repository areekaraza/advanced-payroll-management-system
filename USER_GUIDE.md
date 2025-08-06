# User Guide - Advanced Payroll Management System

## Getting Started

### Starting the Application
1. Navigate to the project directory
2. Run `./run.sh` (Linux/Mac) or compile and run manually
3. The system will display the main menu

### First Time Setup
- The system will create necessary directories automatically
- No employees will be present initially
- All data is saved automatically when you exit

## Menu Navigation

### Main Menu Options
- **1. Employee Management**: Add, view, update, delete employees
- **2. Payroll Operations**: Calculate salaries, process payroll
- **3. Reports & Analytics**: Generate various reports
- **4. System Settings**: Backup, restore, system information
- **0. Exit System**: Save data and exit

## Employee Management

### Adding New Employees

#### Full-Time Employee
1. Select "Employee Management" → "Add New Employee"
2. Choose "Full-Time Employee"
3. Enter required information:
   - Employee ID (unique identifier)
   - First Name and Last Name
   - Email address
   - Department
   - Monthly Salary (between $1,000 - $1,000,000)
   - Monthly Benefits (between $0 - $50,000)

**Features:**
- Fixed monthly salary regardless of hours
- Benefits package included
- Overtime pay for hours > 160/month
- Progressive tax rates (5%, 10%, 15%)

#### Part-Time Employee
1. Select "Employee Management" → "Add New Employee"
2. Choose "Part-Time Employee"
3. Enter required information:
   - Employee ID, Name, Email, Department
   - Hourly Rate ($10 - $500)
   - Maximum Hours per Week (1 - 40)

**Features:**
- Paid based on hours worked
- Overtime at 1.5x rate after 40 hours/week
- Lower tax rates (3%, 8%, 12%)
- Benefits eligibility if working 20+ hours/week

#### Contract Employee
1. Select "Employee Management" → "Add New Employee"
2. Choose "Contract Employee"
3. Enter required information:
   - Employee ID, Name, Email, Department
   - Contract Amount ($1,000 - $10,000,000)
   - Contract End Date (YYYY-MM-DD format)
   - Project Name

**Features:**
- Fixed contract amount
- Pro-rata payment based on time elapsed
- Project-based work tracking
- Flat 20% tax rate

### Viewing Employees
- **View All Employees**: Shows formatted table of all employees
- **Search Employee**: Search by ID, name, or department
- **Individual Details**: View complete employee information

### Updating Employees
1. Select "Update Employee"
2. Enter Employee ID
3. Choose what to update:
   - Basic Information (name, email)
   - Salary/Rate Information
   - Hours Worked
   - Department
   - Status (Active/Inactive)

### Deleting Employees
1. Select "Delete Employee"
2. Enter Employee ID
3. Confirm deletion (type "yes")

## Payroll Operations

### Monthly Payroll Calculation
- Calculates salaries for all active employees
- Shows gross salary, tax deductions, and net salary
- Displays totals and employee counts
- Includes employee type and status information

### Individual Salary Report
- Enter Employee ID to view detailed salary breakdown
- Shows base salary, bonuses, overtime, benefits
- Displays tax calculations and net salary
- Includes employee information and service details

### Update Hours Worked
- Enter Employee ID and new hours
- System recalculates salary automatically
- Shows updated salary information
- Validates hour limits based on employee type

### Overtime Processing
- Shows all employees with overtime hours
- Calculates overtime pay automatically
- Displays overtime hours and pay amounts
- Helps identify workload distribution

## Reports & Analytics

### Department-wise Report
- Groups employees by department
- Shows employee count per department
- Calculates total and average salaries
- Lists all employees in each department

### Employee Type Analysis
- Breaks down employees by type (Full-time, Part-time, Contract)
- Shows percentage distribution
- Calculates cost per employee type
- Helps with workforce planning

### Salary Statistics
- Total, average, highest, and lowest salaries
- Identifies highest and lowest paid employees
- Provides statistical overview
- Useful for budgeting and planning

### Tax Summary Report
- Shows tax calculations for all employees
- Displays gross salary, tax amount, and tax rate
- Calculates total tax collections
- Helps with tax reporting and compliance

### Export Reports
- Generates timestamped report files
- Exports to text files in reports/ directory
- Includes employee list, payroll, and statistics
- Files can be shared or archived

## System Settings

### Backup Data
- Creates backup of all employee data
- Saves to payroll_backup.ser file
- Can be restored later if needed
- Recommended before major changes

### Restore Data
- Restores data from backup file
- Replaces current data with backup
- Useful for recovery from errors
- Confirms before proceeding

### System Information
- Shows system details and statistics
- Displays employee counts
- Shows current date and Java version
- Useful for troubleshooting

### Reset All Data
- Deletes ALL employee data
- Requires typing "CONFIRM" to proceed
- Cannot be undone without backup
- Use with extreme caution

## Tips and Best Practices

### Employee Management
- Use meaningful Employee IDs (e.g., EMP001, ADMIN001)
- Keep employee information up to date
- Regularly backup data before major changes
- Use consistent department names

### Salary Processing
- Update hours worked regularly for accurate calculations
- Review overtime reports to manage workload
- Check tax calculations for compliance
- Generate reports for record keeping

### Data Management
- Export reports regularly for record keeping
- Create backups before system updates
- Use meaningful project names for contract employees
- Keep email addresses current for communication

### System Usage
- Exit the system properly to save data
- Use the search function to quickly find employees
- Review reports for insights into workforce costs
- Update employee status when they leave

## Troubleshooting

### Common Issues
1. **Employee ID already exists**: Use a unique ID for each employee
2. **Invalid date format**: Use YYYY-MM-DD format for dates
3. **Number out of range**: Check min/max values for salary and hours
4. **Employee not found**: Verify the Employee ID is correct

### Data Recovery
- Use "Restore Data" if data appears corrupted
- Check if backup file exists before restoring
- Contact system administrator if problems persist

### Performance
- The system handles hundreds of employees efficiently
- Large reports may take a few seconds to generate
- Export functions create files in the reports/ directory

## Support and Maintenance

### Regular Maintenance
- Backup data weekly or before major changes
- Review inactive employees and archive if needed
- Update salary scales and tax rates as needed
- Clean up old report files periodically

### Data Security
- Keep backup files secure
- Limit access to the system directory
- Regular password updates for system access
- Secure disposal of old data files

---

For technical support or questions about the system, please contact the system administrator or developer.
