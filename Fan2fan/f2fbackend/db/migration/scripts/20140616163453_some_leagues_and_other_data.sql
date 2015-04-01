--// some league and other data
-- Migration SQL that makes the change goes here.

--
-- alter tables
--
alter table `league` change `name` `name` varchar(50) not null;
alter table `team` change `name` `name` varchar(50) not null;

--
-- insert
--
SELECT @adminId := id from `user` where email = 'f2fteam@yahoo.com';

INSERT INTO league (`name`, `desc`, logoUrl, creatorId)
VALUES ('England Primere League', '', '', @adminId);

INSERT INTO league (`name`, `desc`, logoUrl, creatorId)
VALUES ('Italian Serie A', '', '', @adminId);

INSERT INTO league (`name`, `desc`, logoUrl, creatorId)
VALUES ('German Bundesliga', '', '', @adminId);

INSERT INTO league (`name`, `desc`, logoUrl, creatorId)
VALUES ('Spanish Primere Liga', '', '', @adminId);

INSERT INTO league (`name`, `desc`, logoUrl, creatorId)
VALUES ('French Ligue 1', '', '', @adminId);


--//@UNDO
-- SQL to undo the change goes here.

-- insert
SELECT @adminId := id from `user` where email = 'f2fteam@yahoo.com';
delete from `league` where creatorId = @adminId;

-- alter
alter table `league` change `name` `name` varchar(15) not null;
alter table `team` change `name` `name` varchar(25) not null;


