REM Start a team of Jason BDI agents with different roles.
cd ./BDIAgentKrislet
start java -cp .;jason-2.3.jar Krislet -team BDI -asl goalie.asl
timeout 1
start java -cp .;jason-2.3.jar Krislet -team BDI -asl defender.asl
timeout 1
start java -cp .;jason-2.3.jar Krislet -team BDI -asl midfielder.asl
timeout 1
start java -cp .;jason-2.3.jar Krislet -team BDI -asl attacker.asl
timeout 1
start java -cp .;jason-2.3.jar Krislet -team BDI -asl attacker.asl
cd ..
timeout 1