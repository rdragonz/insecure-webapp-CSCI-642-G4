-- Create the appointments table
CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    date VARCHAR(10) NOT NULL,
    time VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL
);
-- Sample data for October
INSERT INTO appointments (date, time, description) VALUES 
('2024-10-01', '09:00', 'Team Standup Meeting'),
('2024-10-01', '14:00', 'Project Review with Client'),
('2024-10-05', '11:00', 'Doctor Appointment'),
('2024-10-10', '16:00', 'Code Review Session'),
('2024-10-15', '10:30', 'Marketing Strategy Meeting');

-- Sample data for November
INSERT INTO appointments (date, time, description) VALUES 
('2024-11-01', '13:00', 'Financial Report Review'),
('2024-11-03', '09:30', 'New Hire Orientation'),
('2024-11-10', '15:00', 'Product Demo'),
('2024-11-12', '14:30', 'Quarterly Roadmap Update'),
('2024-11-20', '08:00', 'Conference Call with Partner');
