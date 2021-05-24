DROP DATABASE `gestoreprogettidb` ;
CREATE DATABASE  IF NOT EXISTS `gestoreProgettiDB` ;
USE `gestoreProgettiDB`;

DROP TABLE IF EXISTS `progetto`;
CREATE TABLE `progetto` (
  `id` int NOT NULL,
  `nome` varchar(50),
  PRIMARY KEY (`id`)
) ;

DROP TABLE IF EXISTS `compito`;
CREATE TABLE `compito` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(50) NOT NULL,
  `descrizione` varchar(128) NOT NULL,
  `stato` ENUM ('1', '2', '3'),
  `progetto_id` int NOT NULL,
  CONSTRAINT `FK_progetto` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  PRIMARY KEY (`id`)
) ;

INSERT INTO `progetto` VALUES (1, "Titolo ") ;
INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES ("Lorem", "ipsum dolor sit amet", 1, 1), ("Ipsum", "ipsum dolor sit amet", 2, 1), ("Dolor", "ipsum dolor sit amet", 3, 1), ("Sit", "ipsum dolor sit amet", 1, 1);
