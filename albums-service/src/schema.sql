CREATE DATABASE `AlbumsDB`;

Use AlbumsDB;

CREATE TABLE `Albums` (
  `isrc` varchar(45) NOT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `coverart` blob,
  PRIMARY KEY (`isrc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

