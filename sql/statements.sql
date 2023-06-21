CREATE TABLE Project (
                         id int not null auto_increment,
                         name varchar(64) not null,
                         description varchar(255),
                         PRIMARY KEY (id)
);

INSERT INTO Project (name, description) VALUES ('First Project', 'This is a description of a the 1st project');
INSERT INTO Project (name, description) VALUES ('Second Project', 'Description of a the 2nd project');

-----------------------------------------------------------------------

CREATE TABLE Issue (
                       id int not null auto_increment,
                       name varchar(128) not null,
                       description varchar(255),
                       author varchar(128) not null,
                       projectId int not null,
                       PRIMARY KEY (id),
                       FOREIGN KEY (projectId) REFERENCES Project(id)
);

INSERT INTO Issue (name, description, author, projectId) VALUES ('Form validation problem', 'Email is not validated', 'Enrico Oliosi', 1);



-----------------------------------------------------------------------

CREATE TABLE UserProject (
                             id int not null auto_increment,
                             username varchar(128) not null,
                             projectId int not null,
                             PRIMARY KEY (id),
                             FOREIGN KEY (projectId) REFERENCES Project(id)
);

INSERT INTO UserProject (username, projectId) VALUES ('eoliosi', 1);
INSERT INTO UserProject (username, projectId) VALUES ('ecostanzi', 2);

CREATE TABLE Comment (
                         id int not null auto_increment,
                         text varchar(2048),
                         author varchar(128) not null,
                         issueId int not null,
                         PRIMARY KEY (id),
                         FOREIGN KEY (issueId) REFERENCES Issue(id)
);


insert into Comment (text, author, issueId) VALUES ('This is a comment', 'ecostanzi', 1);
insert into Comment (text, author, issueId) VALUES ('This is another comment', 'ecostanzi', 1);

--------------------------------------------------------------------------------

CREATE TABLE User (
                      id int not null auto_increment,
                      username varchar(32) not null,
                      name varchar(64) not null,
                      surname varchar(64) not null,
                      PRIMARY KEY (id)
);

INSERT INTO User (username, name, surname) VALUES ('mrossi', 'Mario', 'Rossi');
INSERT INTO User (username, name, surname) VALUES ('gverdi', 'Giuseppe', 'Verdi');

ALTER TABLE Project ADD COLUMN (authorId int);

ALTER TABLE Project ADD FOREIGN KEY (authorId) REFERENCES User(id);

UPDATE Project
SET authorId = (SELECT ID from User WHERE username = 'mrossi')
WHERE ID = 1;

UPDATE Project
SET authorId = (SELECT ID from User WHERE username = 'gverdi')
WHERE ID = 2;

