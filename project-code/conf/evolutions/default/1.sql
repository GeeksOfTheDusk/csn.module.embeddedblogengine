# Blog scheme

# --- !Ups

CREATE TABLE Blogentry (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	belogs_to bigint(20) NOT NULL,
	title varchar(255) NOT NULL,
	content text NOT NULL,
	creation_time timestamp NOT NULL,
	allow_commnets boolean NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Blogcomment (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	author_id bigint(20) NOT NULL,
	content text NOT NULL,
	PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Blogentry;
DROP TABLE Blogcomment;
