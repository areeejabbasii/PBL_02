# Run as Administrator:
#   cd c:\Users\HP\OneDrive\Desktop\oop
#   powershell -ExecutionPolicy Bypass -File .\scripts\enable-sql-server.ps1
#
# Enables TCP/IP, mixed-mode auth (sa), SQL Browser, and restarts SQLEXPRESS.

#Requires -RunAsAdministrator

$ErrorActionPreference = "Stop"
$instance = "SQLEXPRESS"
$version = "MSSQL15"
$saPassword = "Admin123"

Write-Host "=== Online Exam System - SQL Server setup ($instance) ===" -ForegroundColor Cyan

$tcpKey = "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\$version.$instance\MSSQLServer\SuperSocketNetLib\Tcp"
$serverKey = "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\$version.$instance\MSSQLServer"

if (-not (Test-Path $tcpKey)) {
    Write-Error "Registry path not found: $tcpKey. Is $instance installed?"
}

Write-Host "Enabling TCP/IP on port 1433..."
Set-ItemProperty -Path $tcpKey -Name "Enabled" -Value 1 -Type DWord
Set-ItemProperty -Path "$tcpKey\IPAll" -Name "TcpPort" -Value "1433" -Type String
Set-ItemProperty -Path "$tcpKey\IPAll" -Name "TcpDynamicPorts" -Value "" -Type String

Write-Host "Enabling SQL Server and Windows authentication (mixed mode)..."
Set-ItemProperty -Path $serverKey -Name "LoginMode" -Value 2 -Type DWord

Write-Host "Starting SQL Server Browser (helps named-instance tools)..."
Set-Service SQLBrowser -StartupType Manual -ErrorAction SilentlyContinue
Start-Service SQLBrowser -ErrorAction SilentlyContinue

Write-Host "Restarting SQL Server ($instance)..."
Restart-Service "MSSQL`$$instance" -Force
Start-Sleep -Seconds 8

Write-Host "Configuring sa login..."
sqlcmd -S "localhost\SQLEXPRESS" -E -Q "ALTER LOGIN sa WITH PASSWORD = '$saPassword'; ALTER LOGIN sa ENABLE;" | Out-Null

Write-Host "Testing JDBC-style connection (localhost,1433)..."
$result = sqlcmd -S "localhost,1433" -U sa -P $saPassword -Q "SELECT DB_NAME()" -h -1 -W 2>&1
if ($LASTEXITCODE -eq 0) {
    Write-Host "SUCCESS: SQL Server is ready for the Java app." -ForegroundColor Green
    Write-Host "  URL: jdbc:sqlserver://localhost:1433;databaseName=OnlineExamSystem;user=sa;password=$saPassword;..." -ForegroundColor Gray
} else {
    Write-Host "Connection test failed. Output:" -ForegroundColor Yellow
    Write-Host $result
    Write-Host "Try manually in SSMS: connect to localhost,1433 with sa / $saPassword" -ForegroundColor Yellow
}

Write-Host "Done. Run the app with: .\run.bat" -ForegroundColor Cyan
