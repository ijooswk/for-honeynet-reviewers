--// modify user pref page
-- Migration SQL that makes the change goes here.

alter table `user` drop column lastname;
alter table `user` drop column firstname;
alter table `user` add column fullName varchar(50);

alter table `user_detail` add column occupation varchar(50);
alter table `user_detail` change birthyear birthYear smallint(6) unsigned;
alter table `user_detail` change birthmonth birthMonth tinyint(3) unsigned;
alter table `user_detail` change birthday birthDay tinyint(3) unsigned;


--//@UNDO
-- SQL to undo the change goes here.

alter table `user_detail` change birthDay birthday tinyint(3) unsigned not null default '0';
alter table `user_detail` change birthMonth birthmonth smallint(6) unsigned not null default '0';
alter table `user_detail` change birthYear birthyear smallint(6) unsigned not null default '0';
alter table `user_detail` drop column occupation;

alter table `user` drop column fullName;
alter table `user` add column firstname varchar(15) not null default '';
alter table `user` add column lastname varchar(15) not null default '';



