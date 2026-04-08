INSERT INTO technologies (name, category) VALUES
 ('Java', 'Language'),
 ('Spring Boot', 'Framework'),
 ('MySQL', 'Database'),
 ('JavaScript', 'Language'),
 ('React', 'Framework'),
 ('Docker', 'DevOps');

INSERT INTO developers_technologies (developer_id, technology_id) VALUES
(1, 1), (1, 2), (1, 3), -- Ivan: Java, Spring Boot, MySQL
(2, 1), (2, 2), (2, 4), (2, 5), -- Maria: Java, Spring Boot, JS, React
(3, 1), (3, 3), -- Georgi: Java, MySQL
(4, 4), (4, 5), (4, 6), -- Elena: JS, React, Docker
(5, 1), (5, 6);