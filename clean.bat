@echo off
echo =============================
echo Cleaning project...
echo =============================

del /Q src\*.class 2>nul
del /Q IPFinder.jar 2>nul

echo 清理完成！
pause