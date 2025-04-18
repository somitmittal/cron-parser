# Cron Expression Parser

This is a Kotlin implementation of a cron expression parser that parses cron expressions and displays their meaning.

## Input Format

The input is a single command line string which contains 5 cron attributes and 1 command:

## How to Run
```
./gradlew run --args="*/5 0 1-15 * 4-5 /usr/bin/find"
```

## Unit Testing
```
./gradlew test
```