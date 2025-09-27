# Vehicle Service Management System - AI Coding Instructions

## Project Overview
This is a Java-based Vehicle Service Management System project demonstrating Object-Oriented Programming principles with a layered architecture pattern.

## Architecture & Project Structure

### Layered Architecture Pattern
- **Model Layer** (`src/model/`): Domain entities (Customer, Vehicle, Service)
- **Manager Layer** (`src/manager/`): Business logic and data management (CustomerManager, VehicleManager, ServiceManager)
- **UI Layer** (`src/ui/`): User interface components
- **Utility Layer** (`src/util/`): Helper classes like FileHandler for data persistence

### Package Organization
- Use package declarations: `package model;`, `package manager;`, etc.
- Follow Java naming conventions: PascalCase for classes, camelCase for methods/variables
- The `Customer` class in `src/model/Customer.java` exemplifies the expected structure with private fields, constructor, getters, and setters

## Development Workflow

### Building & Running
- VS Code Java extension handles compilation automatically
- Compiled classes go to `bin/` directory (configured in `.vscode/settings.json`)
- Main entry point: `src/App.java` (currently minimal)
- Actual application entry: `src/ui/Main.java` (placeholder for UI implementation)

### File Structure Rules
- Source files: `src/` directory only
- Dependencies: `lib/` directory for JAR files
- Compiled output: `bin/` directory (auto-generated)
- Configuration: `.vscode/settings.json` defines project paths

## Coding Patterns & Conventions

### Model Classes
- Follow the `Customer.java` pattern: private fields, constructor with all parameters, full getter/setter methods
- ID fields should be `int` type for primary keys
- String fields for text data (name, email, address, etc.)
- No setters for ID fields (immutable after creation)

### Manager Classes
- Business logic layer between UI and models
- Handle CRUD operations for their respective entities
- Should interact with `FileHandler` for data persistence
- Currently empty - implement following the business logic pattern

### Data Persistence
- Use `FileHandler` utility class for file-based data storage
- Likely JSON or CSV format for simple data persistence
- No database dependency - file-based approach for learning purposes

## Implementation Guidelines

### When Adding New Features
1. Define model classes first with proper encapsulation
2. Create corresponding manager classes for business logic
3. Implement UI components in the `ui` package
4. Use FileHandler for data persistence needs

### Code Style
- Private fields with public getter/setter methods
- Constructor parameters should match all required fields
- Method names should be descriptive and follow camelCase
- Class names should be descriptive and follow PascalCase

### Testing Strategy
- This is an educational OOP project - focus on demonstrating proper class design
- Manual testing through the main UI application
- No formal testing framework detected

## Key Files to Understand
- `src/model/Customer.java`: Reference implementation for model structure
- `.vscode/settings.json`: Project configuration and build settings
- `src/App.java`: Basic entry point (needs enhancement)
- `src/ui/Main.java`: Intended main application entry (needs implementation)