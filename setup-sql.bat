@echo off
cd /d "%~dp0"
echo.
echo ========================================
echo   SQL Server setup (run as Admin)
echo ========================================
echo.
echo This enables TCP port 1433 and sa login for Java.
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0scripts\enable-sql-server.ps1"
echo.
pause
