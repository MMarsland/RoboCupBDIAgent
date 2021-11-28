cd ./rcssserver-14.0.3-win
start ./rcssserver.exe
cd ..
timeout 1
cd ./rcssmonitor-14.1.0-win
start ./rcssmonitor.exe
cd ..
timeout 1
cd ./BDIAgentKrislet
start java Krislet -team BDI
cd ..
timeout 1
cd ./OriginalKrislet
start java Krislet -team Krislet
