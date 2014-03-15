create user ${db.user} identified by "${db.password}" default tablespace "USERS" temporary tablespace "TEMP" profile "DEFAULT"
/

grant connect to ${db.user}
/
grant resource to ${db.user}
/
grant create view to ${db.user}
/