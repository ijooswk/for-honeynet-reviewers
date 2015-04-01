--// intialdb
-- Migration SQL that makes the change goes here.

--
-- Table structure for table `user`
--
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(40) NOT NULL DEFAULT '',
  `firstname` varchar(15) NOT NULL DEFAULT '' COMMENT 'real firstname',
  `lastname` varchar(15) NOT NULL DEFAULT '' COMMENT 'real lastname',
  `username` varchar(15) NOT NULL DEFAULT '' COMMENT 'username, not need to be the real name',
  `password` char(32) NOT NULL DEFAULT '' COMMENT 'hashed password',
  `activated` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 for not activated, 1 for activated',
  `type` smallint(6) NOT NULL DEFAULT '0' COMMENT '0 for end-user, 1 for pf, 2 for country manager',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `level` smallint(6) NOT NULL DEFAULT '0' COMMENT 'the user level, such as Silver, Gold, Diamond',
  `notifiable` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'receive notifications from the website or not. 1 for true',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_detail`
--
CREATE TABLE `user_detail` (
  `id` int(10) unsigned NOT NULL COMMENT 'stay the same with table user''s id',
  `avatarUrl` varchar(45) NOT NULL DEFAULT '' COMMENT 'the avatar url, set to a default avatar if this is empty',
  `desc` varchar(255) NOT NULL DEFAULT '' COMMENT 'self description',
  `gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 for female, 1 for male, quite vivid O(鈭鈭?O',
  `birthyear` smallint(6) unsigned NOT NULL DEFAULT '0',
  `birthmonth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `birthday` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT 'may be protected',
  `idcardtype` varchar(45) NOT NULL DEFAULT '' COMMENT 'should be protected',
  `idcard` varchar(45) NOT NULL DEFAULT '' COMMENT 'should be protected',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT 'may be protected',
  `nation` varchar(45) NOT NULL DEFAULT '',
  `city` varchar(45) NOT NULL DEFAULT '',
  `points` int(10) NOT NULL DEFAULT '0' COMMENT 'the user''s points, getting from his participation and contribution',
  `phoneActivated` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'not in use yet. activated the phone or not',
  `paytype` varchar(45) NOT NULL DEFAULT '' COMMENT 'not in use yet. the payment''s type',
  `payaccount` varchar(45) NOT NULL DEFAULT '' COMMENT 'not in use yet. the payment''s account',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_language`
--
CREATE TABLE `user_language` (
  `userId` int(10) NOT NULL,
  `language` varchar(45) NOT NULL COMMENT 'language''s key is the expression of language itself, which won''t change, no ID needed',
  UNIQUE KEY `USER_LANGUAGE_KEY` (`userId`,`language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_record`
--
CREATE TABLE `user_record` (
  `id` int(10) unsigned NOT NULL COMMENT 'stay the same with table user''s id',
  `blogs` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'blogs number published',
  `packs` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'packages number published',
  `views` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the number of his page has been viewed',
  `contribBlogs` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the blogs number successfully contributed to a team',
  `sellPacks` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the packages successfully sold',
  `buyPacks` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the packages successfully bought',
  `reviews` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'Review number the user received',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_team`
--
CREATE TABLE `user_team` (
  `userId` int(10) NOT NULL,
  `teamId` int(10) NOT NULL,
  UNIQUE KEY `USER_TEAM_KEY` (`userId`,`teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_token`
--
CREATE TABLE `user_token` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `token` char(36) NOT NULL,
  `userId` int(10) NOT NULL,
  `expires` datetime NOT NULL COMMENT 'the time to be expired',
  `used` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 for not used, 1 for used',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS `user_detail`;
DROP TABLE IF EXISTS `user_record`;
DROP TABLE IF EXISTS `user_token`;
DROP TABLE IF EXISTS `user_team`;
DROP TABLE IF EXISTS `user_language`;
DROP TABLE IF EXISTS `user`;
