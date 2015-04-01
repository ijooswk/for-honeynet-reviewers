--// alter package startTime and endTime to datetime.
-- Migration SQL that makes the change goes here.

ALTER TABLE `package`
CHANGE `startTime` `startTime` DATETIME;
ALTER TABLE `package`
CHANGE `endTime` `endTime` DATETIME;

--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE `package`
CHANGE `startTime` `startTime` TIMESTAMP;
ALTER TABLE `package`
CHANGE `endTime` `endTime` TIMESTAMP;
