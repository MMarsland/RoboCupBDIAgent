# RoboCup Jason BDI Agent
The project is the development of a team of BDI agents using the Jason AgentSpeak
interface to play simulated soccer in the RoboCup Environment. The RoboCup player 
project is based on the Krislet example provided for SYSC 5103 by Babak Esfandiari
(see http://www.sce.carleton.ca/cgi-babak/agentcourse.cgi?RoboCup for more 
information).

This repo is for the SYSC 5103 Final Project.

## Simple Running
This process will allow you to run a simple 5 vs. 5 RoboCup match of 5 BDI
agents playing against 5 regular Krislet Agents.
1. Clone the repo to your machine
2. Read, understand, and run compileCode.bat
3. Read, understand, and run playBDIvsKrislet.bat

NOTE: Never run a .bat file without understanding what it will do.

## Run Team of BDI Agents
To run a team of BDI agents on their own (with no server, monitor, or opponents):
1. Navigate to the robocup/BDIAgentKrislet/ directory.
2. Run the TeamStart.bat file.

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
