# About
This project adds an HTML based UI to the Spring Actuator `/dump` output (raw JSON).

## Installation 
Install it into your own Spring Boot project:
- GIT clone this repository 
- `mvn clean install` ( with jdk8 in your path )
- Add the following to your own Spring Boot Maven POM:

	<groupId>com.moelholm.tools</groupId>
	<artifactId>actuator-ui-dump</artifactId>
	<version>1.0-SNAPSHOT</version>

Done :). _(The first two steps would not be necessary if the artifact where to be found in Maven Central)_

Boot up - and point your browser to: `http://localhost:8080/dump-ui`.

( You can override the `dump-ui` resource location by defining property `actuator-ui.dump.ui` )

## Details
It will pull in `bootstrap` and `jquery` for doing the hard work.

Limitation: Currently the tool only supports accessing the thread dump JSON output at `/dump`   

## Build
Thin JAR:
	
	mvn clean install

Fat Jar

	mvn clean install -Pfatjar