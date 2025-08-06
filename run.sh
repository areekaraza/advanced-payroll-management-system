#!/bin/bash

# Advanced Payroll Management System - Build and Run Script
# This script compiles and runs the Enhanced Payroll Management System

echo "=============================================="
echo "  Advanced Payroll Management System v2.0"
echo "=============================================="

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "Creating bin directory..."
    mkdir -p bin
fi

# Create reports directory if it doesn't exist
if [ ! -d "reports" ]; then
    echo "Creating reports directory..."
    mkdir -p reports
fi

# Compile the Java files
echo "Compiling Java source files..."
javac -d bin src/advancepayrollmanagementsystem/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo "Starting the Payroll Management System..."
    echo "=============================================="
    
    # Run the application
    java -cp bin advancepayrollmanagementsystem.Main
    
    echo "=============================================="
    echo "Thank you for using the Payroll Management System!"
else
    echo "✗ Compilation failed. Please check the source code for errors."
    exit 1
fi
