#!/bin/sh
TEST_NAME=UndoRedoTest
javac -cp ../../../Lib/PDFNet.jar ../../LicenseKey/JAVA/PDFTronLicense.java *.java
java -Djava.library.path=../../../Lib -cp .:../../../Lib/PDFNet.jar:../../LicenseKey/JAVA $TEST_NAME
