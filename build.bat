@echo off
chcp 65001
set "JAVA_HOME=C:\Users\reaqwq\AppData\Roaming\realauncher\meta\java_versions\zulu21.46.19-ca-jre21.0.9-win_x64"
echo Using Java at: %JAVA_HOME%
echo Building Fabric Mod...
call gradlew build
if %ERRORLEVEL% NEQ 0 (
    echo Build Failed!
    pause
    exit /b %ERRORLEVEL%
)
echo Build Successful!
pause
