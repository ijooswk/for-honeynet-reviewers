--// ADD package
-- Migration SQL that makes the change goes here.

DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency`
(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `currency` VARCHAR(45) NOT NULL COMMENT 'the currency name',
  `imageUrl` VARCHAR(45)  NOT NULL DEFAULT '' COMMENT 'the currency image',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `package`;
CREATE TABLE `package`
(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'title of package',
  `content` TEXT NOT NULL COMMENT 'the html content',
  `summary` TEXT NOT NULL COMMENT 'summary',
  `creatorId`  INT(10) NOT NULL,
  `creatorName` VARCHAR(45) NOT NULL,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `language` VARCHAR (45) NOT NULL COMMENT 'the original language of the package',
  `currencyId` int(10) UNSIGNED NOT NULL COMMENT 'the currency',
  `published` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0 for draft, 1 for published',
  `price` MEDIUMINT(8) NOT NULL DEFAULT '0' COMMENT 'the price of the package',
  `views` MEDIUMINT(8) NOT NULL DEFAULT '0' COMMENT 'the view number of the story',
  `ups` MEDIUMINT (8) NOT NULL DEFAULT '0',
  `downs` MEDIUMINT(8) NOT NULL DEFAULT '0',
  `startTime` TIMESTAMP COMMENT 'the start time of the package activity',
  `endTime` TIMESTAMP COMMENT 'the end time of the package activity',
  `maxPeople` MEDIUMINT(8) COMMENT 'the max number of people',
  `minPeople` MEDIUMINT(8) COMMENT 'the min number of people',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`language`) REFERENCES `language` (`language`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS `package`;
DROP TABLE IF EXISTS `currency`;


