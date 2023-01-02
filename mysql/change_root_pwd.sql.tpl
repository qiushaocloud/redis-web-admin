use mysql;
UPDATE mysql.user SET authentication_string=null WHERE User='root';
flush privileges;
flush privileges;
UPDATE user set host = '%' where user = 'root';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '<ROOT_PASSWORD>';
flush privileges;