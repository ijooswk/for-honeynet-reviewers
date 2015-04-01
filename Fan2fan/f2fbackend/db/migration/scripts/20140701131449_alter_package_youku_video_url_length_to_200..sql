--// alter package youku video url length to 200.
-- Migration SQL that makes the change goes here.

ALTER TABLE `package`
CHANGE `youkuVideoId` `youkuVideoId` VARCHAR(200);

--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE `package`
CHANGE `youkuVideoId` `youkuVideoId` VARCHAR(45);

