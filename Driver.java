//Main class
//This class creates an instance of Graph class
//package com.shambhuraj.pathfinder;

import java.util.*;
import java.lang.*;

public class Driver {

    private Graph graph = new Graph();

    public Driver() {

        graph.createGraph();
    }

    //prints menu for the user
    public void printMenu() {

        System.out.println("1. Add a node");
        System.out.println("2. Delete a node");
        System.out.println("3. Add an edge");
        System.out.println("4. Delete an edge");
        System.out.println("5. Shortest path using Bellman Ford Algorithm");
        System.out.println("6. Shortest path using Breadth First Search");
        System.out.println("7. Shortest path using Depth First Search");
        System.out.println("8. Close");

    }

    //takes input and perform respective operations on the graph
    public void menu() {

        boolean keepGoing = true;
        Scanner readInt = new Scanner(System.in);
        Scanner readString = new Scanner(System.in);

        int input;
        String str;

        while (keepGoing) {

            this.printMenu();

            System.out.println("Choose an option.");

            input = readInt.nextInt();


            if (input == 1) {

                System.out.println("Enter the name of the node to be added");
                str = readString.next();

                graph.addNode(str);

            } else if (input == 2) {

                System.out.println("Enter the name of the node to be deleted");
                str = readString.next();

                graph.deleteNode(str);

            } else if (input == 3) {

                String temp;
                int i;

                System.out.println("Enter first node");
                str = readString.next();

                System.out.println("Enter second node");
                temp = readString.next();

                System.out.println("Enter the edge weight");
                i = readInt.nextInt();

                //graph.addEdge(str, temp, i);

            } else if (input == 4) {

                String temp;
                int i;

                System.out.println("Enter first node");
                str = readString.next();

                System.out.println("Enter second node");
                temp = readString.next();

                System.out.println("Enter the edge weight");
                i = readInt.nextInt();

                graph.deleteEdge(str, temp, i);


            } else if (input == 5) {

                graph.resetGraph();

                System.out.println("Enter the source node");
                str = readString.next();

                graph.bellmanFordAlgorithm(str);
                graph.printList();
            } else if (input == 6) {

                graph.resetGraph();

                System.out.println("Enter the source node");
                str = readString.next();

                graph.BFS(str);
                graph.printList();
            } else if (input == 7) {

                graph.resetGraph();

                System.out.println("Enter the source node");

                str = readString.next();

                graph.DFS(str);

                graph.printList();

            } else if (input == 8) {

                System.out.println("Exiting.........");
                keepGoing = false;
                return;
            } else {

                System.out.println("Enter valid input");

            }

        }

    }

    //main method
    public static void main(String[] args) {
        Driver start = new Driver();
        start.menu();
    }
}
