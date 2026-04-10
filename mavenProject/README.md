# Online Exam Slot Manager

## Project Description
The **Online Exam Slot Manager** (ExamSlotPro) is a web-based application designed to streamline the process of scheduling and managing exam slots. It provides an intuitive interface for administrators to manage users, create available exam slots, and handle the booking of these slots by candidates. 

This project is built using a Java backend powered by the **Javalin web framework**, ensuring lightweight, fast, and reliable RESTful API communication. The frontend is built with vanilla HTML, CSS (featuring a modern glassmorphism design), and JavaScript to provide a seamless user experience.

## Features
- **User Management:** Create, view, and delete user profiles.
- **Exam Slot Management:** Add new exam slots with specific dates, times, and locations. View available slots and manage their status.
- **Booking System:** Facilitates the booking of available exam slots by registered users. Prevents double-booking and updates slot availability in real-time.
- **Modern UI:** Responsive, stylish frontend using a modern font (Outfit) and glass-card aesthetics for a premium look and feel.

## Technology Stack
- **Backend:** Java, Javalin Framework
- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Database:** MySQL (JDBC)
- **Build Tool:** Maven
- **Data Serialization:** Jackson Databind
- **Logging:** SLF4J

## How to Run
1. Ensure you have **Java** (JDK 8+) and **Maven** installed.
2. Update your MySQL database credentials in the application (if applicable).
3. Open a terminal in the project root directory.
4. Run the application using the following Maven command:
   ```bash
   mvn clean compile exec:java "-Dexec.mainClass=work.mavenProject.main.WebServer"
   ```
5. Navigate to `http://localhost:7070` in your web browser to access the application.

## API Endpoints

### Users
- `GET /api/users` - Retrieve all users
- `POST /api/users` - Create a new user
- `DELETE /api/users/{id}` - Delete a user by ID

### Exam Slots
- `GET /api/slots` - Retrieve all slots
- `GET /api/slots/available` - Retrieve available slots
- `POST /api/slots` - Create a new slot
- `DELETE /api/slots/{id}` - Delete a slot by ID

### Bookings
- `GET /api/bookings` - Retrieve all bookings
- `POST /api/bookings` - Create a new booking
- `DELETE /api/bookings/{id}` - Delete a booking by ID
