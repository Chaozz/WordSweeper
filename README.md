[![Build Status](https://travis-ci.com/Chaozz/WordSweeper.svg?token=6YffMZSxSQ7Lqc6qFWCq&branch=master)](https://travis-ci.com/Chaozz/WordSweeper)
[![codecov](https://codecov.io/gh/Chaozz/WordSweeper/branch/master/graph/badge.svg?token=lQro98gW7x)](https://codecov.io/gh/Chaozz/WordSweeper)

# WordSweeper-Server
Group project of Taurus @ CS 509
* We test our server with client team Virgo!
* JavaDoc: <https://chaozz.github.io/WordSweeper/javadoc>. Also include in our project.

## Build
You can compile WordSweeper as long as you have the [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) 
for Java 8.

- We use IntelliJ to develop our project. Your can easily build our project in IntelliJ.
- Our code can also works on Eclipse

## Build with Gradle
This part is just a internal instruction, **skip it!**
we use Gradle to build our project from scratch on a remote server. 
#### On Windows
`$ gradlew build`

#### On Linux, BSD, or Mac OS X
`$ ./gradlew build`

#### Build Without Test
`$ ./gradlew build -x test`

#### Test
`$ ./gradlew test`

#### Run
`$ cd build/classes/main`

`$ java server.ServerLauncher`

#### JavaDoc
`$ ./gradlew javadoc`

