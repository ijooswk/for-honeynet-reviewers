--// reward system
-- Migration SQL that makes the change goes here.

--
-- alter
--
ALTER TABLE `userStory` ADD COLUMN `summary` TEXT COMMENT 'the summary of story';
ALTER TABLE `story_contribute` ADD COLUMN `creatorId` INT(10) NOT NULL
COMMENT 'the creator of the story';
ALTER TABLE `story_contribute` ADD COLUMN `creatorName` VARCHAR(15) COMMENT 'the name of the creator';



--//@UNDO
-- SQL to undo the change goes here.

--
-- alter
--
ALTER TABLE `userStory` DROP COLUMN `summary`;
ALTER TABLE `story_contribute` DROP COLUMN `creatorId`;
ALTER TABLE `story_contribute` DROP COLUMN `creatorName`;

