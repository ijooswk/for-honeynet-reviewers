--// add team
-- Migration SQL that makes the change goes here.
--
-- Club or national team
--
create table `team`
(
  `id` int(10) unsigned not null auto_increment,
  `name` varchar(25) not null,
  `desc` varchar(255) not null default '' comment 'description of the team',
  `type` tinyint(3) not null default '0' comment '0 for club, 1 for national team, most would be club',
  `leagueId` int(10) comment 'the belonging leagueId of the team, can be null',
  `city` varchar(45) not null default '' comment 'the city where the team locates',
  `country` varchar(45) not null default '' comment 'the country where the team locates',
  `creatorId` int(10) not null comment 'creator\'s id',
  `createTime` datetime not null comment 'create time',
  `stadium` varchar(45) not null default '' comment 'the home stadium of the team',
  `logoUrl` varchar(45) not null default '' comment 'the logo image url of the team',
  primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table for team internationalization
-- the content is json: {name:xx, desc:xx, city:xx, country:xx, stadium:xx }
--
create table `teamlocale`
(
  `id` int(10) unsigned not null comment 'stay the same with team\'s id',
  `en` varchar(400) comment 'English',
  `de` varchar(400) comment 'Gemany',
  `zh` varchar(400) comment 'Chinese',
  `fr` varchar(400) comment 'Friench',
  `es` varchar(400) comment 'Spanish',
  `ja` varchar(400) comment 'Japanese',
  `kr` varchar(400) comment 'Korea',
  primary key(`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- League
--
create table `league`
(
  `id` int(10) unsigned not null auto_increment,
  `name` varchar(15) not null,
  `desc` varchar(255) not null default '' comment 'description of the league',
  `creatorId` int(10) not null comment 'creator\'s id',
  `createTime` datetime not null comment 'create time',
  `logoUrl` varchar(45) not null default '' comment 'the logo image url of the league',
  primary key(`id`),
  unique key `name`(`name`)
) ENGINE=InnoDB default charset=utf8;

--
-- Table for league internationalization
-- the content is json: {name:xx, desc:xx}
--
create table `leaguelocale`
(
  `id` int(10) unsigned not null comment 'stay the same with league\'s id',
  `en` varchar(400) comment 'English',
  `de` varchar(400) comment 'Gemany',
  `zh` varchar(400) comment 'Chinese',
  `fr` varchar(400) comment 'Friench',
  `es` varchar(400) comment 'Spanish',
  `ja` varchar(400) comment 'Japanese',
  `kr` varchar(400) comment 'Korea',
  primary key(`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


--//@UNDO
-- SQL to undo the change goes here.
drop table if exists `team`;
drop table if exists `teamlocale`;
drop table if exists `league`;
drop table if exists `leaguelocale`;


