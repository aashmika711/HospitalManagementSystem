Hospital Management System
A simple console-based Hospital Management System developed using Java, JDBC, and MySQL. It helps manage patient records, view doctor details, and book appointments through a menu-driven interface.
Features
- Add Patient: Enter a patient's name, age, and gender.
- View Patients: Display the patient records stored in the database.
- View Doctors: Display doctor IDs, names, and specializations.
- Book Appointment: Book an appointment by entering a patient ID, doctor ID, and appointment date.
- Doctor Availability: Check whether the selected doctor already has an appointment on the chosen date before booking.
Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- Eclipse IDE project structure
Project Structure
HospitalManagementSystem/
├── src/
│   ├── hms/
│   │   ├── HospitalManagementSystem.java
│   │   ├── Patient.java
│   │   └── Doctor.java
│   └── module-info.java
└── README.md
Database Setup
1. Install and start MySQL Server.
2. Create a database:
CREATE DATABASE hospital;
USE hospital;
3. Create the tables used by the application:
CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL
);

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patients_id INT NOT NULL,
    doctors_id INT NOT NULL,
    appointments_date DATE NOT NULL,
    FOREIGN KEY (patients_id) REFERENCES patients(id),
    FOREIGN KEY (doctors_id) REFERENCES doctors(id)
);
4. Add doctor records so that appointments can be booked. Example:
INSERT INTO doctors (name, specialization)
VALUES
('Dr. Ravi', 'Cardiology'),
('Dr. Priya', 'Dermatology');
Configuration
The application connects to the local MySQL database named hospital using JDBC. Before running the project, update the database URL, username, and password in HospitalManagementSystem.java to match your MySQL setup.

Security note: Do not commit database passwords to GitHub. Store credentials outside the source code (for example, in environment variables) and change any password that has already been shared in a repository or uploaded project archive.
How to Run
1. Open the project in Eclipse or another Java IDE.
2. Add the MySQL Connector/J JAR to the project's build path/module path.
3. Make sure the MySQL server is running and the hospital database and required tables are created.
4. Configure the database connection details.
5. Run HospitalManagementSystem.java.
6. Select an option from the menu displayed in the console.
Application Menu
Hospital Management System
1. Add Patient
2. View Patients
3. View Doctors
4. Book Appointment
5. Exit
Notes
- Appointment dates should be entered in YYYY-MM-DD format.
- The current availability check prevents a doctor from having more than one appointment on the same date. It does not currently manage appointment time slots.
- The application currently supports adding and viewing patients, viewing doctors, and booking appointments. Updating or deleting records is not implemented in the supplied source code.
