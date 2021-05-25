-- MERGEing admin user to quizdb.mv.db table

MERGE INTO user (id, email, password, roles, user_name)
VALUES 
    (1, 
    'simon.aust@googmail.com', 
    '$2a$10$WkAIpqG0XnxVA1bJucss1.iKggBGXaeNXlKFUmZVDimdkbk1EOrtC', 
    'ADMIN', 
    'Admin');

-- Initial Quiz questions inserted into the quizdb.mv.db table

-- SPORT topic
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (1, false, 'When was Wembley Stadium reopened after it was rebuilt', CURRENT_TIMESTAMP, 'SPORT', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (1, '2007', 1), 
    (2, '1992', 1), 
    (3, '1987', 1), 
    (4, '2020', 1);

MERGE INTO answer (id, answer, quiz_id)
VALUES (1, 1, 1);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (2, false, 'Who won the Wimbledon mens title in 2019', CURRENT_TIMESTAMP, 'SPORT', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (5, 'Roger Federer', 2), 
    (6, 'Rafael Nadal', 2), 
    (7, 'Novac Djokovic', 2), 
    (8, 'Pete Sampras', 2);

MERGE INTO answer (id, answer, quiz_id)
VALUES (2, 3, 2);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (3, false, 'Which country were runners up in the FIFA Women''s World Cup 2019', CURRENT_TIMESTAMP, 'SPORT', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (9, 'Sweden', 3), 
    (10, 'South Korea', 3), 
    (11, 'Norway', 3), 
    (12, 'Netherlands', 3);

MERGE INTO answer (id, answer, quiz_id)
VALUES (3, 4, 3);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES 
    (4, true, 'Which of these teams has LeBron James played for', CURRENT_TIMESTAMP, 'SPORT', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (13, 'Cleveland Cavaliers', 4), 
    (14, 'Los Angeles Lakers', 4), 
    (15, 'Miami Heat', 4), 
    (16, 'Houston Rockets', 4);

MERGE INTO answer (id, answer, quiz_id)
VALUES 
    (4, 1, 4), 
    (5, 2, 4), 
    (6, 3, 4);


-- GENERAL topic

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (5, false, 'What is the captial of Denmark', CURRENT_TIMESTAMP, 'GENERAL', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (17, 'London', 5), 
    (18, 'Malmo', 5), 
    (19, 'Olso', 5), 
    (20, 'Copenhagen', 5);

MERGE INTO answer (id, answer, quiz_id)
VALUES (7, 4, 5);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (6, false, 'What is the population of Earth', CURRENT_TIMESTAMP, 'GENERAL', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (21, '21 Million', 6), 
    (22, '650.5 Million', 6), 
    (23, '4 Billion', 6), 
    (24, '7.8 Billion', 6);

MERGE INTO answer (id, answer, quiz_id)
VALUES (8, 4, 6);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (7, true, 'Which countries border Russia', CURRENT_TIMESTAMP, 'GENERAL', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (25, 'Croatia', 7), 
    (26, 'North Korea', 7), 
    (27, 'China', 7), 
    (28, 'Turkey', 7), 
    (29, 'Finland', 7);

MERGE INTO answer (id, answer, quiz_id)
VALUES (9, 2, 7), (10, 3, 7), (11, 5, 7);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (8, false, 'Who was the author of ''I, Robot''', CURRENT_TIMESTAMP, 'GENERAL', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (30, 'JRR Tolkien', 8), 
    (31, 'Isaac Asimov', 8), 
    (32, 'Dean Koontz', 8), 
    (33, 'Will Smith', 8);

MERGE INTO answer (id, answer, quiz_id)
VALUES (12, 2, 8);

-- MATH topic

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (9, false, 'What is the square root of 25', CURRENT_TIMESTAMP, 'MATH', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (34, '2', 9), 
    (35, '5', 9), 
    (36, '0', 9), 
    (37, '15', 9);

MERGE INTO answer (id, answer, quiz_id)
VALUES (13, 2, 9);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (10, false, 'Which of these is the modulus operator', CURRENT_TIMESTAMP, 'MATH', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (38, '/', 10), 
    (39, '√', 10), 
    (40, '≥', 10), 
    (41, '%', 10);

MERGE INTO answer (id, answer, quiz_id)
VALUES (14, 4, 10);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (11, false, 'What is the answer: 2 * 2 - 1', CURRENT_TIMESTAMP, 'MATH', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (42, '2', 11), 
    (43, '4', 11), 
    (44, '3', 11), 
    (45, '1', 11);

MERGE INTO answer (id, answer, quiz_id)
VALUES (15, 3, 11);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (12, false, 'Solve the equation for x: y = 2x', CURRENT_TIMESTAMP, 'MATH', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (46, 'x = 2y', 12), 
    (47, 'y = x', 12), 
    (48, 'x = y/2', 12), 
    (49, 'x = 2', 12);

MERGE INTO answer (id, answer, quiz_id)
VALUES (16, 3, 12);

-- SCIENCE topic

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (13, false, 'What is the average speed of the International Space Station'
    , CURRENT_TIMESTAMP, 'SCIENCE', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (50, '1500 MPH', 13), 
    (51, '22 200 MPH', 13), 
    (52, '670 MPS', 13), 
    (53, '17 150 MPH', 10);

MERGE INTO answer (id, answer, quiz_id)
VALUES (17, 4, 13);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (14, false, 'Which is the correct chemical symbol for gold'
    , CURRENT_TIMESTAMP, 'SCIENCE', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (54, 'O2', 14), 
    (55, 'Gd', 14), 
    (56, 'Au', 14), 
    (57, 'Cr', 14), 
    (58, 'Si', 14);

MERGE INTO answer (id, answer, quiz_id)
VALUES (18, 3, 14);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (15, false, 'What word desribes a material which isn''t see through', CURRENT_TIMESTAMP, 'SCIENCE', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (59, 'Invisible', 15), 
    (60, 'Opaque', 15), 
    (61, 'Transparent', 15), 
    (62, 'Translucent', 15);

MERGE INTO answer (id, answer, quiz_id)
VALUES (19, 2, 15);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (16, true, 'Which of these are Nobel laureates', CURRENT_TIMESTAMP, 'SCIENCE', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (63, 'Albert Einstein', 16), 
    (64, 'Elon Musk', 16), 
    (65, 'Dr. John C. Mather', 16), 
    (66, 'Stephen Hawking', 16), 
    (67, 'Neil deGrasse Tyson', 16);

MERGE INTO answer (id, answer, quiz_id)
VALUES 
    (20, 1, 16),
    (21, 3, 16);

-- CODING topic

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (17, false, 'What polymorphism in Java', CURRENT_TIMESTAMP, 'CODING', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (68, 'A Java framework', 17), 
    (69, 'An access modifier', 17), 
    (70, 'Many classes that are related by inheritance', 17), 
    (71, 'A static method', 17), 
    (72, 'Java doesn''t have polymorphism', 17);

MERGE INTO answer (id, answer, quiz_id)
VALUES (22, 3, 17);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (18, true, 'Which are correct ways to concatinate a String', CURRENT_TIMESTAMP, 'CODING', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (73, '"hello " + "world"', 18), 
    (74, 'String.join(" ", "hello", "world")', 18), 
    (75, 'new String[]{"hello", " world"}', 18), 
    (76, '"hello" += " world"', 18);

MERGE INTO answer (id, answer, quiz_id)
VALUES 
    (23, 1, 18),
    (24, 2, 18),
    (25, 4, 18);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (19, true, 'Which functional interfaces does Java provide for lambda expressions', CURRENT_TIMESTAMP, 'CODING', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (77, 'Consumer', 19), 
    (78, 'Predicate', 19), 
    (79, 'BiPredicate', 19), 
    (80, 'StringConsumer', 19);

MERGE INTO answer (id, answer, quiz_id)
VALUES 
    (26, 1, 19),
    (27, 2, 19),
    (28, 3, 19);

-- 
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (20, false, 'Who created Java', CURRENT_TIMESTAMP, 'CODING', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (81, 'Linus Torvalds', 20), 
    (82, 'James Gosling', 20), 
    (83, 'Steve Jobs', 20), 
    (84, 'Bill Gates', 20);

MERGE INTO answer (id, answer, quiz_id)
VALUES 
    (29, 2, 20);

-- TOTTENHAM topic

--
MERGE INTO quiz_table (id, is_multiple_choice, text, timestamp, topic, created_by_id) 
VALUES (50, false, 'When was Tottenham Hotspur founded?', CURRENT_TIMESTAMP, 'TOTTENHAM', 1);

MERGE INTO options_table (id, option, quiz_id)
VALUES 
    (100, '2002', 50), 
    (101, '1903', 50), 
    (102, '1882', 50), 
    (103, '1888', 50);

MERGE INTO answer (id, answer, quiz_id)
VALUES (50, 3, 50);