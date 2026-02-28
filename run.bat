@echo off
echo ============================================================
echo  College Event Management System - Build and Run
echo ============================================================

echo.
echo [1/3] Cleaning old class files...
del /s /q *.class 2>nul
for /r %%f in (*.class) do del /q "%%f" 2>nul

echo [2/3] Compiling source files...
javac -cp ".;lib/*" -encoding UTF-8 ^
  Main.java ^
  backend\util\*.java ^
  backend\model\*.java ^
  backend\dao\*.java ^
  backend\service\*.java ^
  frontend\ui\*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed! Check errors above.
    pause
    exit /b %errorlevel%
)

echo [3/3] Compilation successful! Starting application...
echo.
java -cp ".;lib/*" Main

echo.
echo Application closed.
pause
