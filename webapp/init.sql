-- Create the appointments table
CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    date VARCHAR(10) NOT NULL,
    time VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL
);