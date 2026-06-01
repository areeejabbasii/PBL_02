CREATE DATABASE OnlineExamSystem;
GO

USE OnlineExamSystem;
GO

-- Users table with additional profile features
CREATE TABLE users (
    id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    full_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'student',
    profile_picture VARCHAR(255) DEFAULT 'default.png',
    total_exams_taken INT DEFAULT 0,
    average_score DECIMAL(5,2) DEFAULT 0.00,
    created_at DATETIME DEFAULT GETDATE(),
    last_login DATETIME DEFAULT GETDATE()
);

-- Enhanced Questions table with categories and difficulty
CREATE TABLE questions (
    id INT PRIMARY KEY IDENTITY(1,1),
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer CHAR(1) NOT NULL,
    subject VARCHAR(50),
    category VARCHAR(50) DEFAULT 'General',
    difficulty VARCHAR(20) DEFAULT 'Medium',
    marks INT DEFAULT 1,
    time_limit INT DEFAULT 60,
    explanation TEXT,
    created_at DATETIME DEFAULT GETDATE()
);

-- Enhanced Results table with detailed tracking
CREATE TABLE results (
    id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    score INT,
    total_questions INT,
    percentage DECIMAL(5,2),
    time_taken INT,
    exam_date DATETIME DEFAULT GETDATE(),
    exam_type VARCHAR(50) DEFAULT 'Practice',
    questions_attempted INT,
    correct_answers INT,
    wrong_answers INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Question attempts tracking (for analytics)
CREATE TABLE question_attempts (
    id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    question_id INT,
    selected_answer CHAR(1),
    is_correct BIT,
    time_taken INT,
    attempt_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- Bookmarked questions
CREATE TABLE bookmarked_questions (
    id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    question_id INT,
    bookmarked_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    UNIQUE (user_id, question_id)
);

-- Exam sessions (for auto-save functionality)
CREATE TABLE exam_sessions (
    id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    session_data TEXT,
    current_question INT DEFAULT 0,
    time_remaining INT,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Sample exam questions (required for Take Examination)
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, subject, category, difficulty, marks) VALUES
('What is the main principle of Object-Oriented Programming that hides internal details?', 'Inheritance', 'Encapsulation', 'Polymorphism', 'Abstraction', 'B', 'OOP', 'Programming', 'Easy', 1),
('Which keyword is used to create a subclass in Java?', 'implements', 'extends', 'inherits', 'super', 'B', 'Java', 'Programming', 'Easy', 1),
('What does JVM stand for?', 'Java Variable Machine', 'Java Virtual Machine', 'Joint Virtual Module', 'Java Visual Method', 'B', 'Java', 'Programming', 'Medium', 1),
('Which access modifier allows visibility only within the same package?', 'private', 'protected', 'default (package-private)', 'public', 'C', 'Java', 'Programming', 'Medium', 1),
('Polymorphism allows:', 'One name, many forms', 'Multiple inheritance in all languages', 'Only static methods', 'No method overriding', 'A', 'OOP', 'Programming', 'Medium', 1),
('Which collection class does NOT allow duplicate elements?', 'ArrayList', 'HashSet', 'LinkedList', 'Vector', 'B', 'Java', 'Programming', 'Easy', 1),
('What is the superclass of all classes in Java?', 'Main', 'System', 'Object', 'Class', 'C', 'Java', 'Programming', 'Easy', 1),
('Which loop is best when the number of iterations is known?', 'while', 'do-while', 'for', 'switch', 'C', 'Java', 'Programming', 'Easy', 1),
('An abstract class can have:', 'Only abstract methods', 'Only concrete methods', 'Both abstract and concrete methods', 'No constructors', 'C', 'OOP', 'Programming', 'Hard', 1),
('Which SQL command is used to retrieve data?', 'INSERT', 'UPDATE', 'SELECT', 'DELETE', 'C', 'Database', 'Programming', 'Easy', 1);

