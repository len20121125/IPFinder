@echo off
echo ================================
echo   Building IPFinder Project...
echo ================================

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
echo [3] 產生 MANIFEST.MF...
echo Main-Class: %MAINCLASS%> manifest.mf
echo.>> manifest.mf

echo.
echo [4] 打包成 JAR（包含 PNG 與 CLASS）...
jar cfm %JARFILE% manifest.mf -C %SRC% .

if errorlevel 1 (
    echo 打包失敗！
    pause
    exit /b
)

echo.
echo 移除暫存 MANIFEST.MF
del manifest.mf

echo.
echo ================================
echo   完成！輸出檔案:
echo   %JARFILE%
echo ================================

pause
