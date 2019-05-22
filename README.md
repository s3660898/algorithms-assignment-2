To compile on server:
javac -cp .:jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java

To run on server, using example1 and all the optional files, apart from output file, specified:
java -cp .:jopt-simple-5.0.2.jar PathFinderTester -v -t terrain1.para -w waypoints1.para example1.para 


For windows, replace ':' in commands with ';'

BAT files:
run\_xy:   runs the corresponding example tests where x -> task letter, y -> example number
compile:  compiles.
run\_cust: runs cust.para
