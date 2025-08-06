# Contributing to Advanced Payroll Management System

I love your input! I want to make contributing to the Advanced Payroll Management System as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## Pull Requests

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the documentation.
4. Ensure the test suite passes.
5. Make sure your code lints.
6. Issue that pull request!

## Any contributions you make will be under the MIT Software License

In short, when you submit code changes, your submissions are understood to be under the same [MIT License](http://choosealicense.com/licenses/mit/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs using GitHub's [issue tracker](https://github.com/yourusername/advanced-payroll-management-system/issues)

We use GitHub issues to track public bugs. Report a bug by [opening a new issue](https://github.com/yourusername/advanced-payroll-management-system/issues/new).

## Write bug reports with detail, background, and sample code

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

## Development Setup

### Prerequisites
- Java JDK 8 or higher
- Git
- Your favorite IDE (Eclipse, IntelliJ IDEA, VS Code)

### Setting up your development environment

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/yourusername/advanced-payroll-management-system.git
   cd advanced-payroll-management-system
   ```

2. **Compile and test**
   ```bash
   # Compile the project
   javac -d bin src/advancepayrollmanagementsystem/*.java
   
   # Run the application
   java -cp bin advancepayrollmanagementsystem.Main
   ```

3. **Generate sample data for testing**
   ```bash
   ./generate_sample_data.sh
   ```

### Code Style

Please follow these coding standards:

- **Java Naming Conventions**: Use camelCase for variables and methods, PascalCase for classes
- **Indentation**: Use 4 spaces (no tabs)
- **Line Length**: Maximum 120 characters
- **Comments**: Use JavaDoc for public methods and classes
- **Error Handling**: Always handle exceptions appropriately

### Example Code Style

```java
/**
 * Calculates the net salary for an employee after tax deductions.
 * 
 * @param grossSalary The gross salary before deductions
 * @param taxRate The applicable tax rate
 * @return The net salary after tax deduction
 */
public double calculateNetSalary(double grossSalary, double taxRate) {
    if (grossSalary < 0) {
        throw new IllegalArgumentException("Gross salary cannot be negative");
    }
    
    double taxAmount = grossSalary * taxRate;
    return grossSalary - taxAmount;
}
```

## Feature Requests

I welcome feature requests! Please open an issue with:

- **Clear description** of the feature
- **Use case** - why would this feature be useful?
- **Implementation ideas** (if you have any)

### Current areas for contribution

- **Database Integration**: Help migrate from file-based storage to database
- **Web Interface**: Develop a Spring Boot web interface
- **API Development**: Create RESTful APIs
- **Testing**: Add unit tests and integration tests
- **Documentation**: Improve user guides and technical documentation
- **Performance**: Optimize algorithms and memory usage
- **Security**: Enhance data protection and validation

## Testing

Before submitting a pull request, please ensure:

1. **Compilation**: Code compiles without errors
2. **Functionality**: All existing features work correctly
3. **New Features**: Any new features are properly tested
4. **Documentation**: Updated documentation for any changes

### Testing Checklist

- [ ] Application compiles successfully
- [ ] All menu options work correctly
- [ ] Employee CRUD operations function properly
- [ ] Payroll calculations are accurate
- [ ] Reports generate correctly
- [ ] Data persistence works (save/load/backup)
- [ ] Error handling works as expected

## Code Review Process

1. **Automated Checks**: Ensure your code compiles and follows style guidelines
2. **Peer Review**: At least one maintainer will review your pull request
3. **Testing**: We may ask you to add tests or test specific scenarios
4. **Documentation**: Update documentation if your changes affect user-facing features

## Community Guidelines

### Be Respectful
- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Accept constructive criticism gracefully
- Focus on what is best for the community

### Be Collaborative
- Help others learn and grow
- Share knowledge and resources
- Provide helpful feedback on issues and pull requests

### Be Patient
- Understand that maintainers and contributors volunteer their time
- Be patient with the review process
- Ask questions if you're unsure about anything

## Getting Help

If you need help or have questions:

1. **Check Documentation**: Review the [User Guide](USER_GUIDE.md) and [Technical Documentation](TECHNICAL_DOCS.md)
2. **Search Issues**: Look through existing issues to see if your question has been answered
3. **Open an Issue**: Create a new issue with the "question" label
4. **Discussion**: Use GitHub Discussions for general questions and ideas


## License

By contributing, you agree that your contributions will be licensed under the MIT License.

## References

This document was adapted from open-source contribution guidelines and [GitHub's contribution guidelines](https://github.com/github/docs/blob/main/CONTRIBUTING.md).

---

Thank you for contributing to the Advanced Payroll Management System! 
