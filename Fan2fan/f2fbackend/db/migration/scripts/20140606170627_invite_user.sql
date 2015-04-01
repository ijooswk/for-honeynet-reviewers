--// invite user
-- Migration SQL that makes the change goes here.

--
-- Invite user's token table
--
create table `invitecode`
(
  `id` int(10) unsigned not null auto_increment,
  `code` int(8) unsigned not null comment 'the invitation code with 8 digits',
  `type` tinyint(2) unsigned not null comment '1 for pf, 2 for country manager',
  `email` varchar(40) comment 'record who uses this code',
  primary key (`id`),
  unique key `code` (`code`)
) ENGINE=InnoDB default charset=utf8;



--//@UNDO
-- SQL to undo the change goes here.
drop table if exists `invitecode`;


