drop database if exists gig_economy;
create database gig_economy;
use gig_economy;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    bank DECIMAL(10, 2) NOT NULL
);

CREATE TABLE income (
    income_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(250) NOT NULL,
    date DATE NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE goal (
    goal_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    percentage DECIMAL(5, 2) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE expense (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(250) NOT NULL,
    date DATE NOT NULL,
    user_id INT,
    goal_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (goal_id) REFERENCES goal(goal_id) 
);

INSERT INTO user (name, email, bank) VALUES
('Galen', 'galen@example.com', 1300.00),
('Victor', 'victor@example.com', 1500.00),
('Miro', 'miroslav@example.com', 1200.00);

INSERT INTO income (name, amount, description, date, user_id) VALUES
('Freelance', 300.00, 'Front-end web', '2024-05-01', 1),
('Graphic Design', 450.00, 'Logo design', '2024-05-02', 2),
('Consulting', 500.00, 'Business consulting', '2024-05-03', 3);

INSERT INTO goal (description, percentage, user_id) VALUES
('Save for new laptop', 20.00, 1),
('rent', 25.00, 2),
('Groceries', 25.00, 3);

INSERT INTO expense (name, amount, description, date, user_id, goal_id) VALUES
('Groceries', 50.00, 'Stationery items', '2024-05-01', 1, 1),
('Software Subscription', 100.00, 'Monthly subscription', '2024-05-02', 2, 2),
('Travel', 150.00, 'Taxi expenses', '2024-05-03', 3, 3);




