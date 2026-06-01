# Download Microsoft SQL Server JDBC driver into lib\
Set-Location $PSScriptRoot

$url = "https://repo1.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/12.8.1.jre11/mssql-jdbc-12.8.1.jre11.jar"
$out = "lib\mssql-jdbc-12.8.1.jre11.jar"

if (-not (Test-Path "lib")) { New-Item -ItemType Directory -Path "lib" | Out-Null }

Write-Host "Downloading JDBC driver..." -ForegroundColor Cyan
Invoke-WebRequest -Uri $url -OutFile $out -UseBasicParsing

if (Test-Path $out) {
    Write-Host "Done: $out" -ForegroundColor Green
} else {
    Write-Host "Download failed." -ForegroundColor Red
}
