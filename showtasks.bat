call runcrud
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo Compilation error
goto fail

:openbrowser
call explorer http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Open browser with tasks error
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Works is finished