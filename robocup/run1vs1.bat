cd ./rcssserver-14.0.3-win
start ./rcssserver.exe
cd ..
ping localhost
cd ./rcssmonitor-14.1.0-win
start ./rcssmonitor.exe
cd ..
cd ./BDIAgentKrislet
call 1vs1.bat
cd ..
cd ./OriginalKrislet
call 1vs1.bat

