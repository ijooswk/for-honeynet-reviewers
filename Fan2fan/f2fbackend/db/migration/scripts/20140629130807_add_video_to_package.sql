--// add video to package
-- Migration SQL that makes the change goes here.

--  We are going to have youtubeVideoLink, too
alter table package add column youkuVideoId varchar(45) comment 'the video id provided by Youku';


--//@UNDO
-- SQL to undo the change goes here.
alter table package drop column youkuVideoId;



