CREATE TABLE Person (
                        id int not null auto_increment,
                        username varchar(32) not null,
                        name varchar(64) not null,
                        surname varchar(64) not null,
                        PRIMARY KEY (id)
);

INSERT INTO Person (username, name, surname) VALUES ('', 'Mario', 'Rossi');
INSERT INTO Person (username, name, surname) VALUES ('gverdi', 'Giuseppe', 'Verdi');

----------------------------------------------------------------------------

CREATE TABLE Project (
                         id int not null auto_increment,
                         name varchar(64) not null,
                         description varchar(255),
                         authorId int not null,
                         PRIMARY KEY (id),
                         FOREIGN KEY (authorId) REFERENCES Person(id)

);

INSERT INTO Project (name, description, authorId) VALUES ('First Project', 'This is a description of a the 1st project',1);
INSERT INTO Project (name, description, authorId) VALUES ('Second Project', 'Description of a the 2nd project', 2);

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
                             personId int not null,
                             projectId int not null,
                             PRIMARY KEY (id),
                             FOREIGN KEY (projectId) REFERENCES Project(id),
                             FOREIGN KEY (personId) REFERENCES Person(id)
);

INSERT INTO UserProject (personId, projectId) VALUES (1, 1);
INSERT INTO UserProject (personId, projectId) VALUES (2, 2);
-------------------------------------------------------------------------------------------------------

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

