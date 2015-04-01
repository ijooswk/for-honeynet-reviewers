--// update and create time
-- Migration SQL that makes the change goes here.

--
-- changes in user table
--
alter table `user` MODIFY createTime timestamp not null default CURRENT_TIMESTAMP;
alter table `team` modify createTime timestamp not null default CURRENT_TIMESTAMP;
alter table `league` modify createTime timestamp not null default CURRENT_TIMESTAMP;


--//@UNDO
-- SQL to undo the change goes here.

--
-- changes in user table
--
alter table `user` modify `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
alter table `team` modify `createTime` datetime not null;
alter table `league` modify `createTime` datetime not null;
