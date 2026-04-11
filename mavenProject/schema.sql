-- PostgreSQL Schema for Exam Slot Booking System

CREATE TABLE IF NOT EXISTS users (
    userId SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS exam_slots (
    slotId SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    time VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available',
    loc VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    bookingId SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    slotId INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE,
    FOREIGN KEY (slotId) REFERENCES exam_slots(slotId) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (userId, name, email) VALUES 
(1, 'Monika', 'monika@gmail.com'),
(2, 'Arun', 'arun@gmail.com')
ON CONFLICT (userId) DO NOTHING;

INSERT INTO exam_slots (slotId, date, time, status, loc) VALUES 
(101, '2026-03-25', '10:00 AM', 'Available', 'Main Block'),
(102, '2026-03-26', '2:00 PM', 'Available', 'Tindivanam')
ON CONFLICT (slotId) DO NOTHING;
