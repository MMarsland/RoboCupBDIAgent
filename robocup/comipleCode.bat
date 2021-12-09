@echo off

REM Go to the BDI Agent directory.
cd BDIAgentKrislet

REM Run the Java compiler on all JAVA files with the Jason JAR file on the class path.
javac -cp .;jason-2.3.jar *.java

REM Run the Java compiler on all JAVA files with the Jason JAR file on the class path.
REM start java -cp .;jason-2.3.jar JasonAgent

REM Return to the original directory.
cd ..
