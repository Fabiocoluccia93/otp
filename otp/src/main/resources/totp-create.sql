alter session set "_ORACLE_SCRIPT"=true;

CREATE USER DBUSER IDENTIFIED BY "null";

GRANT CONNECT TO DBUSER;

GRANT UNLIMITED TABLESPACE TO DBUSER;

GRANT CONNECT, RESOURCE, DBA TO DBUSER;