CREATE DATABASE 'AlbumsDB';

Use AlbumsDB;

CREATE TABLE 'Albums' (
  'isrc' varchar(45) NOT NULL,
  'title' varchar(45) NOT NULL,
  'description' varchar(45) DEFAULT NULL,
  'year' int(11) NOT NULL,
  'firstname' varchar(45) NOT NULL,
  'lastname' varchar(45) NOT NULL,
  'coverart' blob,
  'mimetype' varchar(45) DEFAULT NULL,
  PRIMARY KEY ('isrc')
);

CREATE TABLE Logs (
    log_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    isrc VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    change_type VARCHAR(255) NOT NULL
);

