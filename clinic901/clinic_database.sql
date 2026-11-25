    email VARCHAR(100),
    address TEXT,
    age INT CHECK (age >= 0)
);

CREATE TABLE IF NOT EXISTS appointments (
    appointment_id SERIAL PRIMARY KEY,
    doctor_id INT NOT NULL REFERENCES doctors(doctor_id),
    patient_id INT NOT NULL REFERENCES patients(patient_id),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL
);

-- Basic users table for the application's login module.
-- Store hashed passwords in real applications; do not store plaintext passwords.
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 5) Sample data
-- These IDs are explicitly provided so sample appointments below match.
INSERT INTO doctors (doctor_id, name, specialty, contact, email, room)
VALUES
    (1, 'Dr. Rahul Sharma', 'Cardiology', '9876543210', 'rahul@example.com', '101'),
    (2, 'Dr. Priya Patil', 'Dermatology', '9876543211', 'priya@example.com', '102'),
    (3, 'Dr. Amit Joshi', 'Orthopedics', '9876543212', 'amit@example.com', '103')
ON CONFLICT (doctor_id) DO NOTHING;

INSERT INTO patients (patient_id, name, contact, email, address, age)
VALUES
    (1, 'Rohan Deshmukh', '9876543213', 'rohan@example.com', 'Pune', 25),
    (2, 'Sneha Kulkarni', '9876543214', 'sneha@example.com', 'Mumbai', 30),
    (3, 'Akash Pawar', '9876543215', 'akash@example.com', 'Nashik', 22)
ON CONFLICT (patient_id) DO NOTHING;

INSERT INTO appointments (appointment_id, doctor_id, patient_id, appointment_date, appointment_time)
VALUES
    (1, 1, 1, '2026-11-26', '10:30:00'),
    (2, 2, 2, '2026-11-27', '11:00:00'),
    (3, 3, 3, '2026-11-28', '12:30:00')
ON CONFLICT (appointment_id) DO NOTHING;

-- Update SERIAL sequences after explicit IDs
SELECT setval(pg_get_serial_sequence('doctors', 'doctor_id'),
              COALESCE((SELECT MAX(doctor_id) FROM doctors), 1),
              EXISTS (SELECT 1 FROM doctors));

SELECT setval(pg_get_serial_sequence('patients', 'patient_id'),
              COALESCE((SELECT MAX(patient_id) FROM patients), 1),
              EXISTS (SELECT 1 FROM patients));

SELECT setval(pg_get_serial_sequence('appointments', 'appointment_id'),
              COALESCE((SELECT MAX(appointment_id) FROM appointments), 1),
              EXISTS (SELECT 1 FROM appointments));

-- 6) Useful SELECT queries

-- View all doctors
SELECT * FROM doctors ORDER BY doctor_id;

-- View all patients
SELECT * FROM patients ORDER BY patient_id;

-- View all appointments
SELECT * FROM appointments ORDER BY appointment_id;

-- Appointment list with doctor and patient names
SELECT
    a.appointment_id,
    d.name AS doctor_name,
    p.name AS patient_name,
    a.appointment_date,
    a.appointment_time
FROM appointments a
JOIN doctors d ON d.doctor_id = a.doctor_id
JOIN patients p ON p.patient_id = a.patient_id
ORDER BY a.appointment_date, a.appointment_time;

-- Search patients by name (example)
SELECT * FROM patients WHERE name ILIKE '%Rohan%';

-- Count doctors and patients
SELECT COUNT(*) AS total_doctors FROM doctors;
SELECT COUNT(*) AS total_patients FROM patients;

-- Appointments for a particular date (example)
SELECT * FROM appointments WHERE appointment_date = '2026-11-26';

-- 7) Example UPDATE queries (commented out; run only when needed)
-- UPDATE doctors SET specialty = 'Neurology' WHERE doctor_id = 1;
-- UPDATE patients SET contact = '9999999999' WHERE patient_id = 1;

-- 8) Example DELETE queries (commented out; run only when needed)
-- DELETE FROM appointments WHERE appointment_id = 1;
-- DELETE FROM patients WHERE patient_id = 1;
-- DELETE FROM doctors WHERE doctor_id = 1;

-- ============================================================
-- End of ClinicCare database setup
-- ============================================================

