package org.example;
import org.example.GraphNodeAL2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller {
    private ArrayList<GraphNodeAL2> graphNodeCol = new ArrayList<GraphNodeAL2>();
//    private HashMap<int num, Room room> g = new HashMap<String, Room>();

    GraphNodeAL2 startNode, endNode;


    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL2<?> startNode, T lookingfor) {
        CostedPath cp = new CostedPath(); //Create result object for cheapest path
        List<GraphNodeAL2<?>> encountered = new ArrayList<>(), unencountered = new ArrayList<>(); //Create encountered/unencountered lists
        startNode.nodeValue = 0; //Set the starting node value to zero
        unencountered.add(startNode); //Add the start node as the only value in the unencountered list to start
        GraphNodeAL2<?> currentNode;
        do { //Loop until unencountered list is empty
            currentNode = unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)
            encountered.add(currentNode); //Record current node in encountered list
            if (currentNode.data.equals(lookingfor)) { //Found goal - assemble path list back to start and return it
                cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
                cp.pathCost = currentNode.nodeValue; //The total cheapest path cost is the node value of the current/goal node
                while (currentNode != startNode) { //While we're not back to the start node...
                    boolean foundPrevPathNode = false; //Use a flag to identify when the previous path node is identified
                    for (GraphNodeAL2<?> n : encountered) { //For each node in the encountered list...
                        for (GraphLinkAL e : n.adjacencyList) //For each edge from that node...
                            if (e.destNode == currentNode && currentNode.nodeValue - e.cost == n.nodeValue) { //If that edge links to the
//current node and the difference in node values is the cost of the edge -> found path node!
                                cp.pathList.add(0, n); //Add the identified path node to the front of the result list
                                currentNode = n; //Move the currentNode reference back to the identified path node
                                foundPrevPathNode = true; //Set the flag to break the outer loop
                                break; //We've found the correct previous path node and moved the currentNode reference
//back to it so break the inner loop
                            }
                        if (foundPrevPathNode)
                            break; //We've identified the previous path node, so break the inner loop to continue
                    }
                }
//Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
                for (GraphNodeAL2<?> n : encountered) n.nodeValue = Integer.MAX_VALUE;
                for (GraphNodeAL2<?> n : unencountered) n.nodeValue = Integer.MAX_VALUE;
                return cp; //The costed (cheapest) path has been assembled, so return it!
            }
//We're not at the goal node yet, so...
            for (GraphLinkAL e : currentNode.adjacencyList) //For each edge/link from the current node...
                if (!encountered.contains(e.destNode)) { //If the node it leads to has not yet been encountered (i.e. processed)
                    e.destNode.nodeValue = Integer.min(e.destNode.nodeValue, currentNode.nodeValue + e.cost); //Update the node value at the end
                    //of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
                    unencountered.add(e.destNode);
                }
            Collections.sort(unencountered, (n1, n2) -> n1.nodeValue - n2.nodeValue); //Sort in ascending node value order
        } while (!unencountered.isEmpty());
        return null; //No path found, so return null
    }

    //Interface method to allow just the starting node and the goal node data to match to be specified
    public static <T> List<GraphNodeAL2<?>> findPathBreadthFirst(GraphNodeAL2<?> startNode, T lookingfor) {
        List<List<GraphNodeAL2<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNodeAL2<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    //Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
    public static <T> List<GraphNodeAL2<?>> findPathBreadthFirst(List<List<GraphNodeAL2<?>>> agenda,
                                                                 List<GraphNodeAL2<?>> encountered, T lookingfor) {
        if (agenda.isEmpty()) return null; //Search failed
        List<GraphNodeAL2<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL2<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
        if (currentNode.data.equals(lookingfor))
            return nextPath; //If that's the goal, we've found our path (so return it)
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for (GraphLinkAL adjLink : currentNode.adjacencyList) //For each adjacent node
            if (!encountered.contains(adjLink.destNode)) { //If it hasn't already been encountered
                List<GraphNodeAL2<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
                newPath.add(0, adjLink.destNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, lookingfor); //Tail call
    }

    //Recursive depth-first search of graph (all paths identified returned)
    public static <T> List<List<GraphNodeAL2<?>>> findAllPathsDepthFirst(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, T lookingfor) {
        List<List<GraphNodeAL2<?>>> result = null, temp2;

        if (from.data.equals(lookingfor)) { //Found it
            List<GraphNodeAL2<?>> temp = new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result = new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }
        if (encountered == null) encountered = new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list
        for (GraphLinkAL adjLink : from.adjacencyList) {
            if (!encountered.contains(adjLink.destNode)) {
                temp2 = findAllPathsDepthFirst(adjLink.destNode, new ArrayList<>(encountered), lookingfor); //Use clone of encountered list
//for recursive call!
                if (temp2 != null) { //Result of the recursive call contains one or more paths to the solution node
                    for (List<GraphNodeAL2<?>> x : temp2) //For each partial path list returned
                        x.add(0, from); //Add the current node to the front of each path list
                    if (result == null)
                        result = temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }
}