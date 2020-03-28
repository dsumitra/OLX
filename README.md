# IntraClassifieds
An OLX kind of solution for internal Amazon employees.

System setup (software): 

Oracle Installation setup for Windows (12c):

https://www.oracle.com/webfolder/technetwork/tutorials/obe/db/12c/r1/Windows_DB_Install_OBE/Installing_Oracle_Db12c_Windows.html

After intalling Oracle, install Toad or any other Oracle IDE.
Used Toad IDE:

https://www.toadworld.com/products/downloads?type=Freeware&download=toad-for-oracle

After installation, run the queries in INIT_TABLES.sql script.

Database is connectes with following credentials.

-----The Credentials-----

Host: jdbc:oracle:thin:@localhost:1521/xepdb1

user= amazon

password=amazon

Used ojdbc8.jar

Platforms used:
---------------
Oracle 12c

Java 13