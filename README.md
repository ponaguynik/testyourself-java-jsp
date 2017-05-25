# TestYourself Java

TestYourself Java is a web application created with Servlets and JSPs, also it uses MySQL database and connected to it through DataSource.

## Getting Started
### Requirements
Java 8, Apache Maven, MySQL, Apache Tomcat 9
### Preparation
Create a new MySQL database with name "testyourself_java".

Execute statement:
```
CREATE TABLE `testyourself_java`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `last_result` TINYINT(4) NULL DEFAULT '0',
  `best_result` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `testyourself_java`.`questions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question` TEXT NULL DEFAULT NULL,
  `code` TEXT NULL DEFAULT NULL,
  `choice` TEXT NULL DEFAULT NULL,
  `choiceType` VARCHAR(45) NULL DEFAULT NULL,
  `answer` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `testyourself_java`.`test_results` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NULL DEFAULT NULL,
  `date` VARCHAR(45) NULL DEFAULT NULL,
  `time` VARCHAR(45) NULL DEFAULT NULL,
  `result` VARCHAR(45) NULL DEFAULT NULL,
  `duration` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
```
Add the latest version of *mysql-connector-java.jar* to *TOMCAT_HOME/lib/*

In *TOMCAT_HOME/conf/server.xml* insert:
```
<Resource name="jdbc/TestYourselfJava" 
			  global="jdbc/TestYourselfJava" 
			  auth="Container" 
			  type="javax.sql.DataSource" 
			  driverClassName="com.mysql.jdbc.Driver" 
			  url="jdbc:mysql://localhost:3306/testyourself_java" 
			  username="root" 
			  password="root" 
			  
			  maxTotal="100" 
			  maxIdle="20" 
			  minIdle="5" 
			  maxWaitMillis="10000"/>
```
In *TOMCAT_HOME/conf/context.xml* insert:
```
<ResourceLink name="jdbc/TestYourselfJavaDB"
              global="jdbc/TestYourselfJava"
              auth="Container"
              type="javax.sql.DataSource" />
```
### Build
Clone the repository:

`git clone https://github.com/ponaguynik/testyourself-java-jsp.git`

Build with maven:

`mvn clean install`

Copy *target/TestYourselfJava.war* file to *TOMCAT_HOME/webapps*
## Usage
Start Tomcat and run TestYourselfJava webapp.
Sign up a new user with username "admin" so when you signed in as "admin" you can add new quesions in test.
