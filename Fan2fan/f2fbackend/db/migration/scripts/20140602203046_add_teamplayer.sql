--// add teamplayer
-- Migration SQL that makes the change goes here.

--
-- player of a team.
-- not need to be too specific, it's not our core business @huangsz
--
CREATE TABLE `player`
(
  `id` int(10) unsigned not null auto_increment,
  `name` varchar(45) not null,
  `desc` varchar(255) not null default '',
  `country` varchar(45) not null default '',
  `number` smallint(4) comment 'player\'s number, can be null',
  `position` varchar(25) not null default '' comment 'the usual field position of the player',
  `imageUrl` varchar(45) not null default '' comment 'the image url of the player',
  `teamId` int(10) not null comment 'the team player plays for',
  `creatorId` int(10) not null comment 'the country manager who created the player info',
  `createTime` timestamp not null default CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- table for player's internationalization.
-- the content is json: {name:xx, desc:xx, position:xx, country:xx }
--
create table `playerlocale`
(
  `id` int(10) unsigned not null comment 'stay the same with player\'s id',
  `en` varchar(400) comment 'English',
  `de` varchar(400) comment 'Gemany',
  `zh` varchar(400) comment 'Chinese',
  `fr` varchar(400) comment 'Friench',
  `es` varchar(400) comment 'Spanish',
  `ja` varchar(400) comment 'Japanese',
  `kr` varchar(400) comment 'Korea',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS `player`;
DROP TABLE IF EXISTS `playerlocale`;



