Put on the map
==============

A web service where users can upload and geo-tag
- images
- link to external data sources like webcams or temperature sensors
- other things that I cannot think of at the moment


# Table of Contents
<!--ts-->
* [Purpose of the project](#purpose-of-the-project)
* [Environments](#environments)
    * [Runtime](#runtime)
    * [Development](#development)
* [Developers guide](#developers-guide)
<!--te-->
# Purpose of the project
The purpose of the project is to demonstrate the power of Jakarta EE 8 (and JSF) and produce
something that might be useful.

# Environments
## Runtime
* OS: Ubuntu 18.04 LTS
* Java: OpenJDK 12
* SQL: Postgresql 10 with PostGIS 2.4.3
* Application server: Wildfly 18

## Development
* OS: Windows 10
* Java: OpenJDK 12
* SQL: Postgresql 10 with PostGIS 2.4.3 (running in a virtual machine)
* Application server: Wildfly 18 

The source code and config files present in this repository are compatible 
with the above listed software versions. The project might not run on different versions.

# Developers guide
## Setting up the environment
### Application server
* Download [Wildfly 18.0.x.Final](https://wildfly.org/downloads/) Java EE Full & Web Distribution and extract it to a suitable folder
* Add a new Management user to it by running either `add-user.bat` or `add-user.sh`

### Database
Instructions for Ubuntu 18.04 LTS:
* Install postgres and postgis:
    * `apt update`
    * `apt install postgresql`
    * `apt install postgis`
* Edit `/etc/postgresql/10/main/postgresql.conf`, uncomment and set listen_addresses to '*' (`listen_addresses = '*'`)
* Add `host    all             all             0.0.0.0/0            md5` to `/etc/postgresql/10/main/pg_hba.conf`
* Restart postgresql `systemctl restart postgresql`
* Switch to the postgres user `sudo su postgres`
* Start psql `psql`
* Change postgres' password so that you can login also remotely `\password`
* Create database `CREATE DATABASE potm-dev;`
* Create db user `create user potm_dev with encrypted password 'potm_dev';`
* Grant privileges `grant all privileges on potm-dev mydb to potm_dev;`
* Enable postgis extension on the potm-dev database:
```$sql
CREATE EXTENSION adminpack;
\c potm-dev
ALTER DATABASE "potm-dev" SET search_path=public, contrib;
\c potm-dev
CREATE EXTENSION postgis SCHEMA public;
```
* Validate by running `SELECT postgis_full_version();` which should return postgis' version.

### IDE
I won't go into details here. You probably know how to import maven projects into your favorite IDE.

### Config files

#### persistence.xml
Set `username`, `password` and `url` in `src/main/resources/META-INF/persistence.xml` and `src/test/resources/META-INF/test-persistence.xml`

#### email.properties
Set correct values in `src/main/resources/email.properties`
