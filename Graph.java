//Graph class
// Implements shortest path algorithms for a directed, undirected, cyclic, edges with negative and 0 weigths
//package com.shambhuraj.pathfinder;

import java.util.*;
import java.lang.*;

public class Graph {

    private Map<Node, HashMap<Node, Integer>> nodeMap = new HashMap<Node, HashMap<Node, Integer>>();

    private Map<String, Node> stringMap = new HashMap<String, Node>();

    private PriorityQueue<Node> nodeQ = new PriorityQueue<Node>();

    private ArrayList<String> pathList = new ArrayList<String>();

    private Queue<Node> bfsQ = new LinkedList<Node>();

    private Deque<Node> dfsStack = new ArrayDeque<Node>();



    //adds the node in the graph
    public void addNode(String nodeName) {

        Node newNode = new Node(nodeName);

        stringMap.putIfAbsent(nodeName, newNode);
        nodeMap.put(newNode, new HashMap<Node, Integer>());
    }

    //deletes he node in the graph
    public void deleteNode(String targetNodeName) {

        Node targetNode = stringMap.get(targetNodeName);

        Set<Map.Entry<Node, HashMap<Node, Integer>>> set = nodeMap.entrySet();

        for (Map.Entry<Node, HashMap<Node, Integer>> h : set) {

            HashMap<Node, Integer> temp = h.getValue();

            if (temp.containsKey(targetNode)) {

                temp.remove(targetNode);
            }

        }

        nodeMap.remove(targetNode);
        stringMap.remove(targetNodeName);
    }

    //adds an edge by taking the sourceNodeName, targetNodename, and the weight of the edge
    public void addEdge(String sourceNodeName, String targetNodeName, int edgeCost, int visualVarityFactor) {

        int totalWeight = (int)(edgeCost + (10 * (1.0/ visualVarityFactor)));

        Node sourceNode = stringMap.get(sourceNodeName);
        Node targetNode = stringMap.get(targetNodeName);

        if ((nodeMap.containsKey(sourceNode)) && (nodeMap.containsKey(targetNode))) {

            nodeMap.get(sourceNode).putIfAbsent(targetNode, totalWeight);
            nodeMap.get(targetNode).putIfAbsent(sourceNode, totalWeight);
        }

    }

    //deletes an edge by taking the related arguments
    public void deleteEdge(String sourceNodeName, String targetNodeName, int edgeCost) {

        Node sourceNode = stringMap.get(sourceNodeName);
        Node targetNode = stringMap.get(targetNodeName);

        if (nodeMap.get(sourceNode).containsKey(targetNode)) {

            nodeMap.get(sourceNode).remove(targetNode);
            nodeMap.get(targetNode).remove(sourceNode);

        }

    }

    //prints Nodes and their shortest paths from the given source node
    public void printList() {

        Set<Map.Entry<Node, HashMap<Node, Integer>>> values = nodeMap.entrySet();

        System.out.println("-----------------------------------------------------");
        System.out.println("Node" + "   ||" + "   Cost" +"   || "+ "Shortest Path");
        System.out.println("-----------------------------------------------------");

        for (Map.Entry<Node, HashMap<Node, Integer>> i : values) {

            System.out.println("  "+i.getKey().getName() + "    ||   " + i.getKey().getPathWeight() +"     ||  "+i.getKey().getThisPath());
            System.out.println("-----------------------------------------------------");

        }
    }

    //almost implements dijkstra's algorithm by kind of bruteforcing
    public void almost_Dijkstra_s(String sourceNodeName) {

        Node sourceNode = stringMap.get(sourceNodeName);
        boolean keepGoing = true;
        int ctr;

        sourceNode.makePathWeightZero();
        sourceNode.updatePreviousNode(null);
        sourceNode.updateHitStatus();


        Set<Map.Entry<Node, HashMap<Node, Integer>>> explorationList = nodeMap.entrySet();

        while(keepGoing) {

            ctr = 0;

            for (Map.Entry<Node, HashMap<Node, Integer>> i : explorationList) {

                if(i.getKey().getHitStatus()) {

                    this.updateNode(i.getKey());
                    ctr++;
                }

                if(ctr == 5) {
                    return;
                }

            }
        }
    }


    //takes a node and updates its neighbours accordingly
    public void updateNode(Node target) {

        int sourcePathWeight = target.getPathWeight();

        HashMap<Node, Integer> map = nodeMap.get(target);

        Set<Map.Entry<Node, Integer>> set = map.entrySet();

        for (Map.Entry<Node, Integer> i : set) {

            if (!i.getKey().getExploredStatus()) {

                if (i.getKey().getPathWeight() > (sourcePathWeight + i.getValue())) {

                    i.getKey().updatePathWeight(sourcePathWeight + i.getValue(), i.getKey().getVisualVarityFactor());
                    i.getKey().updatePreviousNode(target);
                    i.getKey().updateHitStatus();
                }
            }

            target.updateExploredStatus();;
        }

        target.updateExploredStatus();;
    }

    //lazy implementation of Dijkstra's as it finds the shortest path to every single node in the graph
    public void lazy_UpdateNode(Node target) {

        int sourcePathWeight = target.getPathWeight();

        HashMap<Node, Integer> map = nodeMap.get(target);

        Set<Map.Entry<Node, Integer>> set = map.entrySet();

        for (Map.Entry<Node, Integer> i : set) {

            if (!i.getKey().getExploredStatus()) {

                if (i.getKey().getPathWeight() > (sourcePathWeight + i.getValue())) {

                    i.getKey().updatePathWeight(sourcePathWeight + i.getValue(), i.getKey().getVisualVarityFactor());
                    i.getKey().updatePreviousNode(target);

                    if(!this.nodeQ.contains(i.getKey()))
                        this.nodeQ.add(i.getKey());

                }
            }
        }

        target.updateExploredStatus();
    }

    //updates neighbours
    public void bellmanFordAlgorithm(String sourceNodeName) {

        Node sourceNode = stringMap.get(sourceNodeName);

        sourceNode.makePathWeightZero();
        sourceNode.updatePreviousNode(null);
        sourceNode.updateHitStatus();

        nodeQ.add(sourceNode);

        while(!nodeQ.isEmpty()){
            Node n = nodeQ.poll();
            if(!n.getExploredStatus())
                this.lazy_UpdateNode(n);
        }

    }

    //Implements Breadth First Search by using Queue
    public void BFS(String sourceNodeName) {

        Node sourceNode = stringMap.get(sourceNodeName);

        sourceNode.makePathWeightZero();
        sourceNode.updatePreviousNode(null);
        sourceNode.updateHitStatus();

        bfsQ.add(sourceNode);

        while(!bfsQ.isEmpty()) {

            Node n = bfsQ.poll();

            if(!n.getExploredStatus())
                this.BFS_UpdateNode(n);
        }

    }

    //updates neighbours
    public void BFS_UpdateNode(Node target) {

        int sourcePathWeight = target.getPathWeight();

        HashMap<Node, Integer> map = nodeMap.get(target);

        Set<Map.Entry<Node, Integer>> set = map.entrySet();

        for (Map.Entry<Node, Integer> i : set) {

            if (!i.getKey().getExploredStatus()) {

                if (i.getKey().getPathWeight() > (sourcePathWeight + i.getValue())) {

                    i.getKey().updatePathWeight(sourcePathWeight + i.getValue(), i.getKey().getVisualVarityFactor());
                    i.getKey().updatePreviousNode(target);

                    if(!this.bfsQ.contains(i.getKey()))
                        this.bfsQ.add(i.getKey());

                }
            }
        }

        target.updateExploredStatus();
    }

    //implements Depth First Search by using stack
    public void DFS(String sourceNodeName) {

        Node sourceNode = stringMap.get(sourceNodeName);

        sourceNode.makePathWeightZero();
        sourceNode.updatePreviousNode(null);

        dfsStack.push(sourceNode);

        while(!dfsStack.isEmpty()) {


            Node n = dfsStack.pop();

            if(!n.getExploredStatus())
                this.DFS_UpdateNode(n);

            n.updateExploredStatus();
        }

    }

    //updates neighbours
    public void DFS_UpdateNode(Node target) {

        int sourcePathWeight = target.getPathWeight();

        HashMap<Node, Integer> map = nodeMap.get(target);

        Set<Map.Entry<Node, Integer>> set = map.entrySet();

        for (Map.Entry<Node, Integer> i : set) {

            if(!i.getKey().getExploredStatus()) {
                if (!(this.dfsStack.contains(i.getKey()))) {

                    if (i.getKey().getPathWeight() > (sourcePathWeight + i.getValue())) {

                        i.getKey().updatePathWeight(sourcePathWeight + i.getValue(), i.getKey().getVisualVarityFactor());
                        i.getKey().updatePreviousNode(target);

                        this.dfsStack.offerFirst(i.getKey());
                    }
                }
            }
        }

        target.updateExploredStatus();
    }

    //creates initial graph
    public void createGraph() {

        addNode("A");
        addNode("B");
        addNode("C");
        addNode("D");
        addNode("E");
        addNode("F");
        addNode("G");
        addNode("H");

        addEdge("A", "B", 3, 5);
        addEdge("A", "C", 1, 2);
        addEdge("A", "E", 1,  6);
        addEdge("A", "D", 4, 4);
        addEdge("B", "F", 2, 1);
        addEdge("B", "G", 3, 7);
        addEdge("B", "H", 6, 4);
        addEdge("C", "D", 2, 2);
        addEdge("D", "H", 3, 3);
        addEdge("E", "H", 4, 2);
        addEdge("F", "G", 2, 1);
        addEdge("G", "H", 5, 1);


    }

    //resets node parameters to implement other algorithm
    public void resetGraph() {

        Set<Map.Entry<Node, HashMap<Node, Integer>>> values = nodeMap.entrySet();

        for (Map.Entry<Node, HashMap<Node, Integer>> i : values) {
            i.getKey().reset();
        }
    }

}
