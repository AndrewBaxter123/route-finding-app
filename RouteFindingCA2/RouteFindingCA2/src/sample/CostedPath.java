package sample;

import java.util.ArrayList;
import java.util.List;


public class CostedPath {
    public int pathCost=0;
    public List<GraphNodeAL2<?>> pathList=new ArrayList<>();

    /**
     * Gets path cost.
     *
     * @return the path cost
     */
    public int getPathCost() {
        return pathCost;
    }

    /**
     * Sets path cost.
     *
     * @param pathCost the path cost
     */
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * Gets path list.
     *
     * @return the path list
     */
    public List<GraphNodeAL2<?>> getPathList() {
        return pathList;
    }

    /**
     * Sets path list.
     *
     * @param pathList the path list
     */
    public void setPathList(List<GraphNodeAL2<?>> pathList) {
        this.pathList = pathList;
    }
}
