cd ./rcssserver-14.0.3-win
start ./rcssserver.exe
cd ..
ping localhost
cd ./rcssmonitor-14.1.0-win
start ./rcssmonitor.exe
cd ..
cd ./BDIAgentKrislet
call 5vs5.bat
cd ..
cd ./OriginalKrislet
call 5vs5.bat

