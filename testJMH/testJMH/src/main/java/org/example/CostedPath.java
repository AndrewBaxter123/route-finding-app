package org.example;

import java.util.ArrayList;
import java.util.List;

public class CostedPath {
    public int pathCost=0;
    public List<GraphNodeAL2<?>> pathList=new ArrayList<>();

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public List<GraphNodeAL2<?>> getPathList() {
        return pathList;
    }

    public void setPathList(List<GraphNodeAL2<?>> pathList) {
        this.pathList = pathList;
    }
}
