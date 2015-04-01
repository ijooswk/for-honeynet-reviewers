--// big change on package
-- Migration SQL that makes the change goes here.
alter table package drop column content;
alter table package
add column `desc` text not null comment 'the description of the package',
add column `items` text not null comment 'the items included in the package, seperated by some special characters';

drop table if exists packageTrip;
create table packageTrip
(
  `id` int(10) unsigned not null auto_increment,
  `place` varchar(50) not null default '' comment 'the place of this trip',
  `summary` text not null comment 'summary of the trip',
  `desc` text not null comment 'the description of the trip',
  `imageUrl` varchar(45) not null default '' comment 'the image of the trip',
  `packageId` int(10) unsigned not null,
  primary key(`id`),
  foreign key (`packageId`) references `package` (`id`)
) engine = InnoDB default charset = utf8;

--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE IF EXISTS `packageTrip`;
alter table package drop column `desc`, drop column `items`;
alter table package add column content text not null comment 'the html content';



