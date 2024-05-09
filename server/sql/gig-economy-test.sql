drop database if exists gig_economy_test;
create database gig_economy_test;
use gig_economy_test;

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
    name VARCHAR(250) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(250) NOT NULL,
    date DATE NOT NULL,
    user_id INT,
    goal_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (goal_id) REFERENCES goal(goal_id) ON DELETE SET NULL
);

delimiter //
create procedure set_known_good_state()
begin

	delete from expense;
    alter table expense auto_increment = 1;
    delete from goal;
    alter table goal auto_increment = 1;
    delete from income;
    alter table income auto_increment = 1;
    delete from user;
    alter table user auto_increment = 1;


INSERT INTO user (name, email, bank) VALUES
('Galen', 'galen@example.com', 1300.00),
('Victor', 'victor@example.com', 1500.00),
('Miro', 'miroslav@example.com', 1200.00),
('Delete Guy', 'delete@example.com', 1000.00);

INSERT INTO income (name, amount, description, date, user_id) VALUES
('Freelance', 300.00, 'Front-end web', '2024-05-01', 1),
('Graphic Design', 450.00, 'Logo design', '2024-05-02', 2),
('Consulting', 500.00, 'Business consulting', '2024-05-03', 3),
('Delivery', 200.00, 'Food delivery', '2023-04-03', 3);

INSERT INTO goal (description, percentage, user_id) VALUES
('Save for new laptop', 20.00, 1),
('rent', 25.00, 2),
('Groceries', 25.00, 3);

INSERT INTO expense (name, amount, description, date, user_id, goal_id) VALUES
('Groceries', 50.00, 'Stationery items', '2024-05-01', 1, 1),
('Software Subscription', 100.00, 'Monthly subscription', '2024-05-02', 2, 2),
('Travel', 150.00, 'Taxi expenses', '2024-05-03', 3, 3),
('Travel', 50.00, 'Taxi expenses', '2023-04-03', 3, 3);

end //

delimiter ;