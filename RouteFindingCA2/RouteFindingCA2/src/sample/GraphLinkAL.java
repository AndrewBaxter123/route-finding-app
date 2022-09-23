package sample;

public class GraphLinkAL {
    public GraphNodeAL2<?> destNode;
    public int cost;

    /**
     * Instantiates the Graph Link.
     *
     * @param destNode the dest node
     * @param cost     the cost
     */
    public GraphLinkAL(GraphNodeAL2<?> destNode, int cost) {
        this.destNode = destNode;
        this.cost = cost;
    }

    /**
     * Gets our destination node
     *
     * @return the dest node
     */
    public GraphNodeAL2<?> getDestNode() {
        return destNode;
    }

    /**
     * Sets our destination node.
     *
     * @param destNode the dest node
     */
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

    /**
     * Gets the cost
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost.
     *
     * @param cost the cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}