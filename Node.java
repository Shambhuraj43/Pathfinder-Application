//Node class
//Creates a node of the graph
//package com.shambhuraj.pathfinder;


public class Node implements Comparable<Node> {

    private String name;
    private String prevNodeName;
    private int weight;
    private int visualVarityFactor;
    private int pathWeight;
    private boolean exploredStatus;
    private boolean hitStatus;
    private Node prevNode;
    private String path ="";

    Node(String cityName) {

        this.name = cityName;
        this.weight = Integer.MAX_VALUE;
        this.visualVarityFactor = 0;
        this.pathWeight = this.weight + this.visualVarityFactor;	;
        this.exploredStatus = false;
        this.hitStatus = false;

    }

    //constructor
    public String getName() {
        return this.name;

    }

    //setter
    public void makePathWeightZero(){
        this.pathWeight = 0;
    }

    //setter
    public void updatePathWeight(int edgeCost, int visualVarity) {
        this.weight = edgeCost;
        this.visualVarityFactor = visualVarity;

        this.pathWeight =  edgeCost + visualVarity;
    }

    //getter
    public int getPathWeight() {

        return this.pathWeight;
    }

    //getter
    public int getVisualVarityFactor() {

        return this.visualVarityFactor;
    }

    //setter
    public void updateExploredStatus() {

        this.exploredStatus = true;
    }

    //getter for the explored status
    public boolean getExploredStatus() {

        return this.exploredStatus;
    }

    //updates the previous node which has updated its pathweight because it was the minimum through the previous node
    public void updatePreviousNode(Node previousNode) {

        this.prevNode = previousNode;
    }

    public Node getPreviousNode() {

        return this.prevNode;
    }



    public String getNamePrevNode() {

        if(this.prevNode == null) {

            return "No previous Node";
        }

        return this.prevNode.getName();

    }

    //return the shortest path by tracking previous nodes of every node
    public String getThisPath() {

        return this.returnPrevNode(this.prevNode);

    }


    public String returnPrevNode(Node target) {

        if(target == null) {

            this.path =  this.getName() + this.path ;
            return this.path;
        }

        this.path = this.path +" >> "+ target.getName();
        this.returnPrevNode(target.prevNode);
        return this.path;

    }

    //return true if the node has been visited
    public void updateHitStatus() {

        this.hitStatus = true;
    }

    public boolean getHitStatus() {

        return this.hitStatus;
    }

    // compares two nodes by their pathweights for the priority queue implementation for Dijkstra's algorithm
    public int compareTo(Node other) {

        if(other.getPathWeight() < this.getPathWeight())
            return 1;
        else if(other.getPathWeight() > this.getPathWeight())
            return -1;
        else
            return 0;
    }

    //reintializes some parameters
    public void reset() {

        this.pathWeight = Integer.MAX_VALUE	;
        this.exploredStatus = false;
        this.hitStatus = false;
        this.path = "";
    }

}
