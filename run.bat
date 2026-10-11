@echo off
set JDK=C:\Users\%USERNAME%\.jdks\openjdk-26.0.2\bin
if not exist "%JDK%\javac.exe" set JDK=C:\Users\Aryan Yadav\.jdks\openjdk-26.0.2\bin
echo Compiling...
"%JDK%\javac.exe" -cp "lib\mysql-connector-j-8.0.33.jar" -d bin -sourcepath src src\com\ecommerce\model\*.java src\com\ecommerce\util\*.java src\com\ecommerce\dao\*.java src\com\ecommerce\dao\impl\*.java src\com\ecommerce\service\*.java src\com\ecommerce\ui\*.java
if errorlevel 1 (echo COMPILE FAILED & pause & exit /b 1)
echo Running...
"%JDK%\java.exe" -cp "bin;lib\mysql-connector-j-8.0.33.jar" com.ecommerce.ui.ECommerceGUI
