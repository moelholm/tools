A tool for organizing photos and videos into my personal favorite layout.

# Run with Docker ( users )
`docker run -v target/input:/mnt/input -v target/output:/mnt/output nickymoelholm/media-organizer`

This will move all your media files from `target/input` into an organized layout under `target/output`.
 
# Developers - read this:
Before you begin: Ensure that you have jdk 8 in the path.

## Run the application
`mvn spring-boot:run -Drun.arguments="--fromDir=/path/to/source --toDir=/path/to/destination"`

## Test
`mvn test`
( Runs all unit tests and integration tests )

## Build distribution
`mvn package`
( Find a fat JAR in the target folder )
  

