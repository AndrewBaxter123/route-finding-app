package sample;


import java.util.ArrayList;
import java.util.List;

/**
 * The type Graph node al 2.
 *
 * @param <T> the type parameter
 */
public class GraphNodeAL2<T> {
    public T data;
    public int nodeValue=Integer.MAX_VALUE;
    public List<GraphLinkAL> adjacencyList = new ArrayList<>(); //Could use any List implementation

    /**
     * Instantiates the Graph Node.
     *
     * @param data the data
     */
    public GraphNodeAL2(T data) {
        this.data = data;
    }

    /**
     * Connect to node directed, not used.
     *
     * @param destNode the dest node
     * @param cost     the cost
     */
    public void connectToNodeDirected(GraphNodeAL2<T> destNode,int cost) {
        adjacencyList.add( new GraphLinkAL(destNode,cost) ); //Add new link object to source adjacency list
    }

    /**
     * Connect to node undirected, the one we used.
     *
     * @param destNode the dest node
     * @param cost     the cost
     */
    public void connectToNodeUndirected(GraphNodeAL2<T> destNode,int cost) {
        adjacencyList.add( new GraphLinkAL(destNode,cost) ); //Add new link object to source adjacency list
        destNode.adjacencyList.add( new GraphLinkAL(this,cost));
    }


    /**
     * Gets data of the object added
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data for the object.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets adjacency list of the object adding.
     *
     * @return the adjacency list
     */
    public List<GraphLinkAL> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Sets adjacency list for the object added.
     *
     * @param adjacencyList the adjacency list
     */
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


