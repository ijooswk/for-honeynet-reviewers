--// reward policy
-- Migration SQL that makes the change goes here.

--
-- Alter table
--
ALTER TABLE `user` CHANGE `type` `type` TINYINT(3) NOT NULL DEFAULT '0'
COMMENT '0 for end-user, 1 for pf, 2 for country manager';

ALTER TABLE `user_detail` ADD COLUMN `province` VARCHAR(45) NOT NULL DEFAULT '';


--
-- Single Reward Rule
--
CREATE TABLE `reward_simple_rule`
(
  `id` int(10) unsigned not null auto_increment,
  `trigger` varchar(45) not null comment 'the rewardIdentifier of the rule',
  `points` smallint(6) not null default '0' comment 'the points user\'ll get',
  `userType` mediumint (8) not null default '0' comment 'the required userType. last digit for endUser, second last for PF, third last for CF',
  primary key (`id`)
)ENGINE =InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `reward_simple_rule` (`trigger`, `points`, `userType`) VALUES ('SIGNUP', 10, 0x1);

--
-- Number Reward Rule
--
CREATE TABLE `reward_number_rule`
(
  `id` int(10) unsigned not null auto_increment,
  `trigger` varchar(45) not null comment 'the rewardIdentifier of the rule',
  `points` smallint(6) not null comment 'the points user\'ll get',
  `userType` mediumint (8) not null default '0' comment 'the required userType. last digit for endUser, second last for PF, third last for CF',
  `number` mediumint (8) not null default '0' comment 'reach this value to get the points',
  primary key (`id`)
) ENGINE = InnoDB default charset = utf8;

INSERT INTO `reward_number_rule` (`trigger`, `points`, `userType`, `number`) VALUES ('PAGE_VIEW', 10, 0x10, 1000);
INSERT INTO `reward_number_rule` (`trigger`, `points`, `userType`, `number`) VALUES ('INVITE', 10, 0x11, 50);


--
-- reward history
--
create table `reward_history`
(
  `id` int(10) unsigned not null auto_increment,
  `userId` int(10) not null comment 'the benefited user\'s id',
  `rewardIdentifier` varchar (60) not null comment 'the identifier of the reward, can be combined by rewardIdentifier and other conditions',
  `points` smallint (6) not null comment 'the points user gets',
  `createTime` timestamp not null default CURRENT_TIMESTAMP,
  primary key (`id`)
) engine = InnoDB default charset = utf8;


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS `reward_simple_rule`;
DROP TABLE IF EXISTS `reward_number_rule`;
DROP TABLE IF EXISTS `reward_history`;

--
-- Alter table
--
ALTER TABLE `user` CHANGE `type` `type` SMALLINT(6) NOT NULL DEFAULT '0'
COMMENT '0 for end-user, 1 for pf, 2 for country manager';

ALTER TABLE `user_detail` DROP COLUMN `province`;
