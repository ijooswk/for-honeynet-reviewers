--// add story
-- Migration SQL that makes the change goes here.

--
-- language table
--
create table `language`
(
  `language` varchar(45) not null primary key
)engine=InnoDB default charset=utf8;

--
-- User Story
--
create table `userStory`
(
  `id` int(10) unsigned not null auto_increment,
  `title` varchar(50) not null default '' comment 'The title of the user story',
  `content` text not null comment 'The html content',
  `creatorId` int(10)  not null,
  `createTime` timestamp not null default CURRENT_TIMESTAMP,
  `updateTime` timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatorName` varchar(15) not null comment 'The creator\'s name, just set to be the creator\'s name when create the story. no need to change in the future',
  `type` tinyint(3) not null comment '0 for team story, 1 for package story',
  `language` varchar(45) not null comment 'the original language of the story',
  `published` tinyint(1) not null default '0' comment '0 for draft, 1 for published',
  `views` mediumint(8) not null default '0' comment 'the view number of the story',
  `ups` mediumint (8) not null default '0',
  `downs` mediumint(8) not null default '0',
  primary key (`id`),
  constraint `FK_LANGUAGE` foreign key (`language`) references `language` (`language`)
)ENGINE=InnoDB default charset=utf8;

--
-- story contribution
--
create table `story_contribute`
(
  `storyId` int(10) not null comment 'the story to contributed',
  `teamId` int(10) not null comment 'the team to contribute the story to',
  `approved` tinyint(1) not null default '0' comment '0 not approved, 1 for got approved by countryManager',
  `reviewerId` int(10) comment 'the reviewer\'s id, reviewer should be a country manager',
  `reviewerName` varchar(15) comment 'no need to change, just the userName of reviewer when he reviews',
  unique key `STORY_CONTRIBUTE_KEY` (`storyId`, `teamId`)
)engine = InnoDB default charset=utf8;


--
-- Alter
--
-- ALTER TABLE `user_language` ADD CONSTRAINT `FK_LANGUAGE` FOREIGN KEY (`language`) REFERENCES `language` (`language`);

--
-- Insert
--
INSERT INTO `language` (`language`) VALUES ('English');
INSERT INTO `language` (`language`) VALUES ('中文');



--//@UNDO
-- SQL to undo the change goes here.
drop table if exists `userStory`;
drop table if exists `story_contribute`;
DROP TABLE IF EXISTS `language`;

-- alter
-- ALTER TABLE `user_language` DROP FOREIGN KEY `FK_LANGUAGE`;





