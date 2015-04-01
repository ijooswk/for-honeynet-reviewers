--// add_name_zh_to_countries_cities
-- Migration SQL that makes the change goes here.
ALTER TABLE `cities` ADD `name_zh` VARCHAR(50) NULL AFTER `name_en`;
ALTER TABLE `countries` ADD `name_zh` VARCHAR(50) NULL AFTER `name_en`;

--//@UNDO
-- SQL to undo the change goes here.
ALTER TABLE `countries` DROP `name_zh`;
ALTER TABLE `cities` DROP `name_zh`;