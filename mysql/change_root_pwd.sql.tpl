use mysql;
UPDATE mysql.user SET authentication_string=null WHERE User='root';
flush privileges;
UPDATE user set host = '%' where user = 'root';
flush privileges;
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '<ROOT_PASSWORD>';
flush privileges;