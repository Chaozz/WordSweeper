[![Build Status](https://travis-ci.com/Chaozz/WordSweeper.svg?token=6YffMZSxSQ7Lqc6qFWCq&branch=master)](https://travis-ci.com/Chaozz/WordSweeper)
# WordSweeper
Group project for Taurus @ CS 509
## Build
You can compile WordSweeper as long as you have the [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) 
for Java 8.
### On Windows
`$ gradlew build`

### On Linux, BSD, or Mac OS X
`$ ./gradlew build`

### Build Without Test
`$ ./gradlew build -x test`

## Test
`$ ./gradlew test`

## Run
`$ cd build/classes/main`

`$ java server.ServerLauncher`
