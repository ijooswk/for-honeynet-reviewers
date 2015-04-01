--// more reward policy
-- Migration SQL that makes the change goes here.

--
-- alter table
--
alter table `invitecode` add column `invitorId` int(10) not null comment 'the invitor\'s id';
alter table `user_record` add column `invites` mediumint (8) not null default '0' comment 'the number of user invited by this user';
alter table `user_record` change `blogs` `stories`mediumint(8) NOT NULL DEFAULT '0' COMMENT 'stories number published';
alter table `user_record` change `contribBlogs` `contribStories` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the story number successfully contributed to a team';

--
-- fix bugs
--
update `reward_number_rule` set userType = 0b10 where userType=0x10;
update `reward_number_rule` set userType = 0b11 where userType=0x11;


--//@UNDO
-- SQL to undo the change goes here.
alter table `invitecode` drop column `invitorId`;
alter table `user_record` drop column `invites`;
alter table `user_record` change `stories` `blogs` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'stories number published';
alter table `user_record` change `contribStories` `contribBlogs` mediumint(8) NOT NULL DEFAULT '0' COMMENT 'the story number successfully contributed to a team';

--
-- fix bugs
--
update `reward_number_rule` set userType = 0x10 where userType=0b10;
update `reward_number_rule` set userType = 0x11 where userType=0b11;
