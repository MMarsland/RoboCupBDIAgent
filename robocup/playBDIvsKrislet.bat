REM Start the RoboCup server.
cd ./Server
start ./rcssserver.exe
cd ..
timeout 1

REM Start the RoboCup monitor.
cd ./Monitor
start ./rcssmonitor.exe
cd ..
timeout 1

REM Start a team of Jason BDI agents with different roles.
cd ./BDIAgentKrislet
start java -cp .;jason-2.3.jar Krislet -team BDI -asl goalie.asl
start java -cp .;jason-2.3.jar Krislet -team BDI -asl defender.asl
start java -cp .;jason-2.3.jar Krislet -team BDI -asl midfielder.asl
start java -cp .;jason-2.3.jar Krislet -team BDI -asl attacker.asl
start java -cp .;jason-2.3.jar Krislet -team BDI -asl attacker.asl
cd ..
timeout 1

REM Start a team of default Krislet players.
cd ./OriginalKrislet
start java Krislet -team Krislet
start java Krislet -team Krislet
start java Krislet -team Krislet
start java Krislet -team Krislet
start java Krislet -team Krislet
cd ..
