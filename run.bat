@echo off
echo �️  Compiling Mini OS Simulator...
javac -d . src/main/*.java src/main/scheduler/*.java src/main/memory/*.java src/main/process/*.java
if %errorlevel% neq 0 (
    echo  Compilation failed!
    pause
    exit /b 1
)

echo  Compilation successful!
echo.
echo � Starting Mini OS Simulator...
java main.Main

echo.
echo � Simulation completed!
pause
