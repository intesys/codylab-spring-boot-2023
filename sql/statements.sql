CREATE TABLE Project (
                         id int not null auto_increment,
                         name varchar(64) not null,
                         description varchar(255),
                         PRIMARY KEY (id)
);

INSERT INTO Project (name, description) VALUES ('First Project', 'This is a description of the 1st project');
INSERT INTO Project (name, description) VALUES ('Second Project', 'This is a description is a of the 2nd project');
INSERT INTO Project (name, description) VALUES ('Third Project', 'This is a description of the 3rd project');
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

INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue1', 'description of issue1', 'Gino', 1);
INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue2', 'description of issue2', 'Mino', 1);
INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue3', 'description of issue3', 'Pino', 2);
INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue4', 'description of issue4', 'Dino', 2);
INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue5', 'description of issue5', 'Gino', 3);
INSERT INTO Issue (name, description, author, projectId) VALUES ('Issue6', 'description of issue6', 'Dino', 3);


-----------------------------------------------------------------------

CREATE TABLE UserProject (
                             id int not null auto_increment,
                             username varchar(128) not null,
                             projectId int not null,
                             PRIMARY KEY (id),
                             FOREIGN KEY (projectId) REFERENCES Project(id)
);

INSERT INTO UserProject (username, projectId) VALUES ('mattia', 1);
INSERT INTO UserProject (username, projectId) VALUES ('zeno', 2);
INSERT INTO UserProject (username, projectId) VALUES ('alessia', 3);

CREATE TABLE Comment (
                         id int not null auto_increment,
                         text varchar(2048),
                         author varchar(128) not null,
                         issueId int not null,
                         PRIMARY KEY (id),
                         FOREIGN KEY (issueId) REFERENCES Issue(id)
);


insert into Comment (text, author, issueId) VALUES ('This is the first comment', 'alessia', 1);
insert into Comment (text, author, issueId) VALUES ('This is the second comment', 'alessia', 1);
insert into Comment (text, author, issueId) VALUES ('This is the first comment', 'zeno', 2);
insert into Comment (text, author, issueId) VALUES ('This is the second comment', 'zeno', 2);
insert into Comment (text, author, issueId) VALUES ('This is the first comment', 'mattia', 3);