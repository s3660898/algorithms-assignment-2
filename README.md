To compile on server:
javac -cp .:jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java

To run on server, using example1 and all the optional files, apart from output file, specified:
java -cp .:jopt-simple-5.0.2.jar PathFinderTester -v -t terrain1.para -w waypoints1.para example1.para 
