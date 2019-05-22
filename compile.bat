echo off
javac -cp .;jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java -Xlint:deprecation
pause
