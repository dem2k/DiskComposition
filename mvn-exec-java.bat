@call mvn exec:java -Dexec.mainClass="Main.App" -Dexec.args="argument1" -Dexec.args="argument2"
@if errorlevel 1 pause
