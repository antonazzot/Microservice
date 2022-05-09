create table if not exists "storage" (
id serial not null primary key,
storagetype varchar (50),
bucketname varchar (50),
filepath varchar (200),
filename varchar (200),
storagedate date
)