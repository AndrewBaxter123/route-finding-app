package org.example;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL2<T> {
    public T data;
    public int nodeValue=Integer.MAX_VALUE;
    public List<GraphLinkAL> adjacencyList = new ArrayList<>(); //Could use any List implementation

    public GraphNodeAL2(T data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNodeAL2<T> destNode,int cost) {
        adjacencyList.add( new GraphLinkAL(destNode,cost) ); //Add new link object to source adjacency list
    }
    public void connectToNodeUndirected(GraphNodeAL2<T> destNode,int cost) {
        adjacencyList.add( new GraphLinkAL(destNode,cost) ); //Add new link object to source adjacency list
        destNode.adjacencyList.add( new GraphLinkAL(this,cost));
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<GraphLinkAL> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<GraphLinkAL> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    @Override
    public String toString() {
        StringBuilder all= new StringBuilder();
        for(GraphLinkAL theRoom: adjacencyList){
            all.append(theRoom.toString());
        }
        return "GraphNodeAL2{" +
                "data=" + data +
                ", adjacencyList=" + all +
                '}';
    }
}
