@echo off
echo =============================
echo Running IPFinder...
echo =============================

if not exist IPFinder.jar (
    echo 找不到 IPFinder.jar，先執行 build.bat 編譯！
    pause
    exit /b
)

java -jar IPFinder.jar
echo.
echo 程式執行完畢。
pause
