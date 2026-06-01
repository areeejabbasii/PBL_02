@echo off
cd /d "%~dp0"

if not exist "lib\mssql-jdbc-12.8.1.jre11.jar" (
    echo ERROR: JDBC driver missing in lib\
    echo Download: https://repo1.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/12.8.1.jre11/mssql-jdbc-12.8.1.jre11.jar
    pause
    exit /b 1
)

if not exist "bin" mkdir bin

echo Compiling...
javac -encoding UTF-8 -cp "lib\*;src" -d bin src\main\Main.java src\database\*.java src\gui\*.java src\model\*.java src\service\*.java src\utils\*.java
if errorlevel 1 (
    echo Compile failed.
    pause
    exit /b 1
)

echo Starting application...
java -cp "bin;lib\*" main.Main
