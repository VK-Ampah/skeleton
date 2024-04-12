# Developer Guide for Health Monitoring Application

## Overview

This application is designed to manage users' health data, medicine reminders, and provide health recommendations.

## Application Structure

### Classes and Their Functions

- **DatabaseConnection**: Manages the database connection to PostgreSQL.
- **User**: Defines the user properties and methods.
- **UserDao**: Handles all database operations related to users like registration, deletion, and authentication.
- **Doctor**: Inherits User, additional properties for doctors.
- **DoctorPortalDao**: Manages doctor-specific data interactions like managing patients.
- **HealthData**: Stores and manages individual health data records.
- **HealthDataDao**: Handles CRUD operations for health data.
- **MedicineReminder**: Represents a single medicine reminder.
- **MedicineReminderManager**: Manages all reminders for a user.
- **Recommendation**: Stores recommendations generated based on health data.
- **RecommendationSystem**: Generates health recommendations based on user health data.

## Key Operations

### User Management

- **Create User**: Checks if user exists by email before creating a new user.
- **Retrieve User**: Retrieves users from the database.
- **Delete User**: Verifies user existence by ID before deletion.
- **User Authentication**: Validates user credentials.

### Health Data Management

- **Add Health Data**: Includes validation to prevent duplicate records.
- **Retrieve Health Records**: Retrieves health record for a user.
- **Update Health Data**: Checks existing data before update.
- **Delete Health Data**: Validates existence before deletion.

### Medicine Reminder Management

- **Add Reminder**: Adds new reminders to the database and manages timing.
- **Update Reminder**: Ensures reminders are current and relevant.
- **Delete Reminder**: Removes outdated or irrelevant reminders.
- **Check Reminder status**: Check for due reminders fpr users.

### Doctor portal
- **Reteives Doctor**: Retrieve doctor records.
- **Retrieve patients Health Records**: Retrieve patients records.
- **Add Patients Realtionship**: Add doctor pateints relationshp.

### Generating Recommendations

- **Generate Recommendations**: Based on health metrics such as heart rate and BMI.
- **Persist Recommendations**: Stores recommendations in the database for user reference.

## Database Schema

### Tables

- **users**: Stores user information.
- **health_data**: Logs health metrics for users.
- **medicine_reminders**: Manages medication taking schedules.
- **recommendations**: Stores generated recommendations for users.
- **doctor_patient**: Stores doctor patient relationships.

## Setup and Configuration

### Dependencies

- Java JDK 8+
- PostgreSQL
- BCrypt for Java

### Building the Application

Compile the Java files using `javac`, ensuring all dependencies are included in the classpath. Example:
bash : `javac -cp postgresql-42.2.5.jar:bcrypt-0.4.jar *.java`
