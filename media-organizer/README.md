A tool for organizing photos and videos into my personal favorite layout.

# Run with Docker ( users )
`docker run -e "FROMDIR=/mnt/input" -e "TODIR=/mnt/output" -v target/input:/mnt/input -v target/output:/mnt/output nickymoelholm/media-organizer`

This will move all your media files from `target/input` into an organized layout under `target/output`.
- The `-e` arguments tell the tool to use `/mnt/input` and `/mnt/output` inside the docker container
- The `-v` arguments is how you map your real disk folders into `/mnt/input` and `/mnt/output` inside the docker container
 
# Guide for developers
Before you begin: Ensure that you have jdk 8 in the path.

## Run the application
`mvn spring-boot:run -Drun.arguments="--fromDir=/path/to/source --toDir=/path/to/destination"`

You can also run the application with the `--daemon` flag. It will:
- Start the application as a daemon (running until explicitly closed)
- Schedule the organizing process to run according to whatever has been configured in `application.properties` (see the application sources for examples).  
 
Do you want to try out the tool with Dropbox? Then:
- Use the `--filesystemtype=dropbox` flag
- And set the System property `-Ddropbox.accessToken=[your-own-token]`.

( to use this feature you need to get your own token for your Dropbox account )
 
## Test
`mvn test`
( Runs all unit tests and integration tests )

## Build distribution
`mvn package`
( Find a fat JAR in the target folder )
  

