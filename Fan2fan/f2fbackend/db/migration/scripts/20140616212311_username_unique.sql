--// username unique
-- Migration SQL that makes the change goes here.

--
-- alter
--
ALTER TABLE `user` ADD UNIQUE KEY (`username`);


--//@UNDO
-- SQL to undo the change goes here.
ALTER TABLE `user` DROP INDEX `username`;


