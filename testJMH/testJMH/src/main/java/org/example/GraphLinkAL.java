package org.example;

public class GraphLinkAL {
    public GraphNodeAL2<?> destNode; //Could also store source node if required
    public int cost;

    public GraphLinkAL(GraphNodeAL2<?> destNode, int cost) {
        this.destNode = destNode;
        this.cost = cost;
    }

    public GraphNodeAL2<?> getDestNode() {
        return destNode;
    }

    public void setDestNode(GraphNodeAL2<?> destNode) {
        this.destNode = destNode;
    }

    @Override
    public String toString() {
        return "GraphLinkAL{" +
                ", destNode=" + destNode.data +
                ", cost=" + cost +
                '}';
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
