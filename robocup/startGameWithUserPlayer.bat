cd ./Server
start ./rcssserver.exe
cd ..
timeout 1
cd ./Monitor
start ./rcssmonitor.exe
cd ..
timeout 1
cd ./UserPlayer
start java Krislet -team User
cd ..