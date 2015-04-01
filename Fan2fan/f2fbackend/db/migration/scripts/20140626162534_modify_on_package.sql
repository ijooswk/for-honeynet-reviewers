--// modify on package
-- Migration SQL that makes the change goes here.
alter table package modify `desc` text comment 'the description of the package';
alter table packageTrip modify `imageUrl` varchar(150) not null default '' comment 'the image url of this trip';
alter table currency modify `imageUrl` varchar(150) not null default '';
alter table player modify `imageUrl` varchar(150) not null default '';
alter table team modify `logoUrl` varchar(150) not null default '';
alter table user_detail modify `avatarUrl` varchar(150) not null default '';


--//@UNDO
-- SQL to undo the change goes here.
alter table user_detail modify `avatarUrl` varchar(45) not null default '';
alter table team modify `logoUrl` varchar(45) not null default '';
alter table player modify `imageUrl` varchar(45) not null default '';
alter table currency modify `imageUrl` varchar(45) not null default '';
alter table packageTrip modify `imageUrl` varchar(45) not null default '' comment 'the image url of this trip';
alter table package modify `desc` text not null comment 'the description of the package';

