CREATE TABLE Project (
    id int not null,
    name varchar(64) not null,
    description varchar(255),
    PRIMARY KEY (id)
);

INSERT INTO Project VALUES (1, 'First Project', 'This is a description of a the 1st project');

-----------------------------------------------------------------------

CREATE TABLE Issue (
     id int not null,
     name varchar(128) not null,
     description varchar(255),
     author varchar(128) not null,
     projectId int not null,
     PRIMARY KEY (id),
     FOREIGN KEY (projectId) REFERENCES Project(id)
);

INSERT INTO Issue VALUES (1, 'Form validation problem', 'Email is not validated', 'Enrico Oliosi', 1);

