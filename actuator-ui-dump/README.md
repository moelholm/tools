# About
This project adds an HTML based UI to the Spring Actuator `/dump` output (raw JSON):

![Image](https://github.com/nickymoelholm/documentation/blob/master/actuator-ui-dump/dump-ui.png?raw=true)

## Installation 
Install it into your own Spring Boot project:

Add the following to your own Spring Boot Maven POM:
	
	<dependency>
		<groupId>com.moelholm.tools</groupId>
		<artifactId>actuator-ui-dump</artifactId>
		<version>0.2</version>
	</dependency>

Boot up - and point your browser to: `http://localhost:8080/dump-ui`.

( You can override the `dump-ui` resource location by defining property `actuator-ui.dump-ui.path` ) 

## Details
It will pull in `bootstrap` and `jquery` for doing the hard work.  

## Build
Thin JAR:
	
	mvn clean install

Fat Jar

	mvn clean install -Pfatjar
