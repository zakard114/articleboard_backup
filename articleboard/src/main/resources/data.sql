INSERT INTO article(title, content) VALUES('aaaa', '1111');
INSERT INTO article(title, content) VALUES('bbbb', '2222');
INSERT INTO article(title, content) VALUES('cccc', '3333');

INSERT INTO article(title, content) VALUES('What is your favorite movie?', 'Comment Go');
INSERT INTO article(title, content) VALUES('What is your soul food?', 'Comment Go Go');
INSERT INTO article(title, content) VALUES('What is your hobby?', 'Comment Go Go Go');

INSERT INTO comment(article_id, nickname, body) VALUES(4, 'Park', 'Good Will Hunting');
INSERT INTO comment(article_id, nickname, body) VALUES(4, 'Kim', 'I am Sam');
INSERT INTO comment(article_id, nickname, body) VALUES(4, 'Choi', 'Shawshank Redemption');

INSERT INTO comment(article_id, nickname, body) VALUES(5, 'Park', 'Chicken');
INSERT INTO comment(article_id, nickname, body) VALUES(5, 'Kim', 'Noodles');
INSERT INTO comment(article_id, nickname, body) VALUES(5, 'Choi', 'Pizza');

INSERT INTO comment(article_id, nickname, body) VALUES(6, 'Park', 'Jogging');
INSERT INTO comment(article_id, nickname, body) VALUES(6, 'Kim', 'Youtube');
INSERT INTO comment(article_id, nickname, body) VALUES(6, 'Choi', 'Reading');

CREATE TABLE if not exists persistent_logins (
    username varchar_ignorecase(100) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
);