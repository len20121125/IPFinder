@echo off
echo ================================
echo   Building IPFinder Project...
echo ================================

REM 設定來源與輸出位置
set SRC=src
set MAINCLASS=IPFinder
set JARFILE=IPFinder.jar

echo.
echo [1] 清除舊的 class 檔...
del /Q %SRC%\%MAINCLASS%.class 2>nul

echo.
echo [2] 編譯 Java 程式...
javac %SRC%\%MAINCLASS%.java
if errorlevel 1 (
    echo 編譯失敗！
    pause
    exit /b
)

echo.
echo [3] 打包成 JAR...
jar cfe %JARFILE% %MAINCLASS% -C %SRC% %MAINCLASS%.class

if errorlevel 1 (
    echo 打包失敗！
    pause
    exit /b
)

echo.
echo ================================
echo   完成！輸出檔案:
echo   %JARFILE%
echo ================================

pause
