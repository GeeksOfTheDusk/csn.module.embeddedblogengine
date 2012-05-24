# Blog scheme

# --- !Ups

CREATE TABLE Blogentry (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	creatorID bigint(20) NOT NULL,
	creatorST varchar(50) NOT NULL,
	title varchar(255) NOT NULL,
	content text NOT NULL,
	creation_time timestamp NOT NULL,
	allow_commnets boolean NOT NULL,
	privacy int NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Blogcomment (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	authorID bigint(20) NOT NULL,
	authorST varchar(50) NOT NULL,
	content text NOT NULL,
	creation_time timestamp NOT NULL,
	belongs_to bigint(20) NOT NULL,
	FOREIGN KEY (belongs_to) REFERENCES Blogentry(id),
	PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Blogentry;
DROP TABLE Blogcomment;
