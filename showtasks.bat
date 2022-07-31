cd C:\Development\Projects\tasks

call runcrud.bat
if "%ERRORLEVEL%" == "0" goto worked
echo.
echo runcrud has errors! breaking work
goto fail

:worked
echo.
echo runcrud run suceed!
start "" http://localhost:8080/crud/v1/tasks

:fail
echo.
echo There were errors
