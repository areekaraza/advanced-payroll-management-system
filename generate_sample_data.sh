#!/bin/bash

# Sample Data Generator for Advanced Payroll Management System
# This script creates sample employees to demonstrate system capabilities

echo "=============================================="
echo "  Sample Data Generator"
echo "=============================================="

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "Creating bin directory..."
    mkdir -p bin
fi

# Compile the Java files first
echo "Compiling Java source files..."
javac -d bin src/advancepayrollmanagementsystem/*.java

if [ $? -ne 0 ]; then
    echo "✗ Compilation failed. Cannot generate sample data."
    exit 1
fi

echo "✓ Compilation successful!"
echo "Creating sample data file..."

# Create a Java program to generate sample data
cat > SampleDataGenerator.java << 'EOF'
import advancepayrollmanagementsystem.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SampleDataGenerator {
    public static void main(String[] args) {
        ArrayList<Employee> sampleEmployees = new ArrayList<>();
        
        // Add Full-Time Employees
        FullTimeEmployee ft1 = new FullTimeEmployee(
            "FT001", "Alice", "Johnson", "alice.johnson@company.com", 
            "Engineering", 6500.0, 975.0
        );
        ft1.setHoursWorked(170); // Some overtime
        
        FullTimeEmployee ft2 = new FullTimeEmployee(
            "FT002", "Bob", "Smith", "bob.smith@company.com", 
            "Marketing", 5500.0, 825.0
        );
        ft2.setHoursWorked(160); // Standard hours
        
        FullTimeEmployee ft3 = new FullTimeEmployee(
            "FT003", "Carol", "Davis", "carol.davis@company.com", 
            "Finance", 7000.0, 1050.0
        );
        ft3.setHoursWorked(165); // Slight overtime
        
        // Add Part-Time Employees
        PartTimeEmployee pt1 = new PartTimeEmployee(
            "PT001", "David", "Wilson", "david.wilson@company.com", 
            "Customer Service", 25.0, 25.0
        );
        pt1.setHoursWorked(100); // 25 hours/week * 4 weeks
        
        PartTimeEmployee pt2 = new PartTimeEmployee(
            "PT002", "Emma", "Brown", "emma.brown@company.com", 
            "Sales", 30.0, 30.0
        );
        pt2.setHoursWorked(120); // 30 hours/week * 4 weeks
        
        PartTimeEmployee pt3 = new PartTimeEmployee(
            "PT003", "Frank", "Miller", "frank.miller@company.com", 
            "IT Support", 35.0, 35.0
        );
        pt3.setHoursWorked(140); // 35 hours/week * 4 weeks
        
        // Add Contract Employees
        ContractEmployee ct1 = new ContractEmployee(
            "CT001", "Grace", "Taylor", "grace.taylor@contractor.com", 
            "Consulting", 15000.0, LocalDate.of(2025, 12, 31), "ERP Implementation"
        );
        
        ContractEmployee ct2 = new ContractEmployee(
            "CT002", "Henry", "Anderson", "henry.anderson@freelance.com", 
            "Development", 8000.0, LocalDate.of(2025, 10, 15), "Mobile App Development"
        );
        
        ContractEmployee ct3 = new ContractEmployee(
            "CT003", "Ivy", "Thomas", "ivy.thomas@consultant.com", 
            "Training", 12000.0, LocalDate.of(2025, 11, 30), "Staff Training Program"
        );
        
        // Add some additional employees for different departments
        FullTimeEmployee ft4 = new FullTimeEmployee(
            "FT004", "Jack", "White", "jack.white@company.com", 
            "HR", 5800.0, 870.0
        );
        ft4.setHoursWorked(158);
        
        PartTimeEmployee pt4 = new PartTimeEmployee(
            "PT004", "Kate", "Green", "kate.green@company.com", 
            "Marketing", 28.0, 20.0
        );
        pt4.setHoursWorked(80); // 20 hours/week * 4 weeks
        
        // Add all employees to the list
        sampleEmployees.add(ft1); sampleEmployees.add(ft2); sampleEmployees.add(ft3); sampleEmployees.add(ft4);
        sampleEmployees.add(pt1); sampleEmployees.add(pt2); sampleEmployees.add(pt3); sampleEmployees.add(pt4);
        sampleEmployees.add(ct1); sampleEmployees.add(ct2); sampleEmployees.add(ct3);
        
        // Save to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("payroll_data.ser"))) {
            oos.writeObject(sampleEmployees);
            System.out.println("✓ Sample data created successfully!");
            System.out.println("✓ " + sampleEmployees.size() + " employees added to the system");
            System.out.println("\nSample Employees Created:");
            System.out.println("=".repeat(60));
            
            for (Employee emp : sampleEmployees) {
                System.out.printf("%-8s %-20s %-15s %-15s%n", 
                    emp.getEmployeeId(), 
                    emp.getFullName(), 
                    emp.getEmployeeType(), 
                    emp.getDepartment());
            }
            
            System.out.println("=".repeat(60));
            System.out.println("You can now run the Payroll System to see the sample data!");
            
        } catch (IOException e) {
            System.out.println("✗ Error creating sample data: " + e.getMessage());
        }
    }
}
EOF

# Compile and run the sample data generator
javac -cp bin SampleDataGenerator.java
java -cp .:bin SampleDataGenerator

# Clean up the generator file
rm SampleDataGenerator.java SampleDataGenerator.class

echo "=============================================="
echo "Sample data generation complete!"
echo "Run ./run.sh to start the system with sample data"
echo "=============================================="
