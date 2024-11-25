CREATE DATABASE scheduler_db;

DROP TABLE IF EXISTS reminders;
DROP TABLE IF EXISTS users;


CREATE TABLE reminders (
    reminder_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    reminder_date DATE NOT NULL,
    reminder_time TIME NOT NULL,
    priority ENUM('HIGH', 'MEDIUM', 'LOW') DEFAULT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Use CURRENT_TIMESTAMP for automatic date and time
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id VARCHAR(40) UNIQUE NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL
);


    