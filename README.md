# easy-pm-tool

A simple project management tool build in java, specialized for building projects.

[![Build Status](https://travis-ci.org/JuKu/easy-pm-tool.svg?branch=master)](https://travis-ci.org/JuKu/easy-pm-tool)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=ncloc)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=coverage)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Technical Debt Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=sqale_index)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=code_smells)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=bugs)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=vulnerabilities)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Apm-tool-parent&metric=security_rating)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Apm-tool-parent) 

[![Sonarcloud](https://sonarcloud.io/api/project_badges/quality_gate?project=com.jukusoft%3Apm-tool-parent)](https://sonarcloud.io/dashboard?id=com.jukusoft%3Apm-tool-parent)

## System requirements

  - you need a mysql database
  
## Setup

All relevant configuration files are placed in directory **config/runtime**.
First, create the database configuration in file **config/runtime/** like this:
```ini
[Database]
; database type, e.q. "mysql", "postgresql" or "sqlite"
type=mysql

; the database driver class (for SQLite: org.sqlite.JDBC)
driver.class.name=com.mysql.jdbc.Driver

[MySQL]
host=<MYSQL_IP>
port=3306
user=<MYSQL_USER>
password=<MYSQL_PASSWORD>
database=<MYSQL_DATABASE>
prefix=pmt_

; hibernate configuration (only change this, if you know, what you do)
hibernate.hbm2ddl.auto=update

;hibernate.dialect=org.hibernate.dialect.PostgreSQL95Dialect


# Allows Hibernate to generate SQL optimized for a particular DBMS (sqlite: "org.hibernate.dialect.SQLiteDialect")
hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```