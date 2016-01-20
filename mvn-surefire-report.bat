@echo off
set report=
set /p report=Run tests (ENTER=No, something else=Yes)? 
if "%report%" == "" goto ronly
call mvn surefire-report:report
goto finish
:ronly
call mvn surefire-report:report-only
:finish
if errorlevel 1 pause
start target/site/surefire-report.html
