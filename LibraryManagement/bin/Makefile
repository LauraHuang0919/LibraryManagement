run: compile
	java -classpath .:lib/* launch

compile: compileData compileBackEnd compileFrontEnd

test: compile TestSuite.class
	java -jar junit5.jar -cp .:lib/* -scan-classpath

clean:
	$(RM) *.class
	$(RM) *~

compileData: Book.class 

Book.class: Book.java
	javac Book.java

compileBackEnd: MapADT.class HashTableMap.class Manegement.class

MapADT.class: MapADT.java
	javac MapADT.java 

HashTableMap.class: HashTableMap.java
	javac HashTableMap.java 

Manegement.class: Manegement.java
	javac -classpath .:lib/*  Manegement.java

compileFrontEnd: launch.class

launch.class: launch.java
	javac launch.java
	
TestSuite.class: TestSuite.java
	javac -cp .:junit5.jar:lib/* TestSuite.java 
