@echo off
setlocal
set TEST_NAME=UndoRedoTest
javac.exe -cp .;../../../Lib/PDFNet.jar;../../LicenseKey/JAVA *.java
java.exe -Djava.library.path=../../../Lib -classpath .;../../../Lib/PDFNet.jar;../../LicenseKey/JAVA %TEST_NAME%
endlocal
