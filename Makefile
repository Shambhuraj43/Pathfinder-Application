
default: Driver.class

Driver.class: Driver.java Graph.class Node.class
	javac Driver.java

Graph.class: Graph.java
	javac Graph.java

Node.class: Node.java
	javac Node.java

run: Driver.class
		java Driver

clean:
	rm *.class
