# Online Exam System - Run (PowerShell)
Set-Location $PSScriptRoot

$jar = "lib\mssql-jdbc-12.8.1.jre11.jar"
if (-not (Test-Path $jar)) {
    Write-Host "ERROR: JDBC driver missing in lib\" -ForegroundColor Red
    Write-Host "Run: .\download-jdbc.ps1" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

if (-not (Test-Path "bin")) { New-Item -ItemType Directory -Path "bin" | Out-Null }

Write-Host "Compiling..." -ForegroundColor Cyan
javac -encoding UTF-8 -cp "lib\*;src" -d bin `
    src\main\Main.java src\database\*.java src\gui\*.java `
    src\model\*.java src\service\*.java src\utils\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compile failed." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Starting application..." -ForegroundColor Green
java -cp "bin;lib\*" main.Main
