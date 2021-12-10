# RoboCup Jason BDI Agent
The project is the development of BDI agents using the Jason AgentSpeak
interface to play simulated soccer in the RoboCup Environment.

This repo is for the SYSC 5103 Final Project.

## Simple Running
This process will allow you to run a simple 5 vs. 5 RoboCup match of 5 BDI
agents playing against 5 regular Krislet Agents.
1. Clone the repo to your machine
2. Read, understand, and run compileCode.bat
3. Read, understand, and run playBDIvsKrislet.bat

NOTE: Never run a .bat file without understanding what it will do.

## Development
Files modified during development include:
-   all the top level .bat files
-   BDIAgentKrislet/
    - AgentSpecifications/
        - All agent .asl files
    - Belief.java
    - Brain.java
    - Intent.java
    - JasonAgent.java
    - Krislet.java
    - Memory.java
