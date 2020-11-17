create user"petuser"@"localhost" identify by "petuser123";
grant all privileges vileges petstoredb.* to "petuser"@"localhost";
flush privileges

drop database if exists petstoredb;
create database petstoredb;