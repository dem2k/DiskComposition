@echo off
set CLASSPATH=target\classes;config
for %%i in ("target\dependency\*.jar") do call :addcp %%i
java %*
goto ende
:addcp
set CLASSPATH=%1;%CLASSPATH%
:ende
