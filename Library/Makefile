run: compile
	java -classpath .:lib/* launch

test: compile libTest.class
	java -jar junit5.jar -cp .:json-simple-1.1.jar --scan-class-path

libTest.class:
	javac -cp .:junit5.jar:lib/* libTest.java 

clean:
	$(RM) *.class
	$(RM) *~

compile: compileData compileBackEnd compileFrontEnd

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

