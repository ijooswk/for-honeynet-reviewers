--// create admin
-- Migration SQL that makes the change goes here.

--
-- create an administrator
-- password is : FAN2fan1730!*))
--
INSERT INTO `user`(email, username, `password`, createTime, notifiable, `type`, activated)
VALUES('f2fteam@yahoo.com', 'admin', 'b8e958152772750a40510572d444d0df', '2014-06-16 09:46:01.0', false, 2, true);

SELECT @adminId := id from `user` where email = 'f2fteam@yahoo.com';

insert into `user_detail`(id) values(@adminId);
insert into `user_record`(id) values(@adminId);

--//@UNDO
-- SQL to undo the change goes here.
SELECT @adminId := id from `user` where email = 'f2fteam@yahoo.com';

delete from `user` where username = 'admin';
delete from `user_detail` where id = @adminId;
delete from `user_record` where id = @adminId;
