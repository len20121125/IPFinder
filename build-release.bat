@echo off
echo ============================================
echo   Building Release + Auto Versioning
echo ============================================

REM 讀取版本號
set /p VERSION=<version.txt
echo 目前版本：v%VERSION%

REM 編譯並產生 JAR
call build.bat

REM 解析版本號
for /f "tokens=1-3 delims=." %%a in ("%VERSION%") do (
    set MAJOR=%%a
    set MINOR=%%b
    set PATCH=%%c
)

REM patch 自動 +1
set /a PATCH=%PATCH%+1
set NEW_VERSION=%MAJOR%.%MINOR%.%PATCH%
echo 新版本：v%NEW_VERSION%

REM 寫回 version.txt
echo %NEW_VERSION% > version.txt

echo 提交變更到 Git...

git add .
git commit -m "Release v%NEW_VERSION%"
git push

echo 建立 Git Tag v%NEW_VERSION%...
git tag v%NEW_VERSION%
git push origin v%NEW_VERSION%

echo ============================================
echo Release 完成！版本更新為：v%NEW_VERSION%
echo ============================================
pause
