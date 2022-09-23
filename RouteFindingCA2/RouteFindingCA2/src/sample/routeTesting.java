package sample;

//import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class routeTesting {
    Room waterford, kilkenny, carlow, wexford, cork, limerick;
    GraphNodeAL2<Room> wat, kilk, carl, wex, cor, lim;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //Graph Nodes hard coded for tests
        Room waterford = new Room("Waterford", "1", "1", "1");
        Room kilkenny = new Room("Kilkenny", "2", "2", "2");
        Room carlow = new Room("Carlow", "3", "3", "3");
        Room wexford = new Room("Wexford", "4", "4", "4");
        Room cork = new Room("Cork", "5", "5", "5");
        Room limerick = new Room("Limerick", "6", "6", "6");

        wat = new GraphNodeAL2(waterford);
        kilk = new GraphNodeAL2(kilkenny);
        carl = new GraphNodeAL2(carlow);
        wex = new GraphNodeAL2(wexford);
        cor = new GraphNodeAL2(cork);
        lim = new GraphNodeAL2(limerick);

        //Waterford/Kilkenny/Carlow/Wexford/Cork=50
        wat.connectToNodeUndirected(kilk, 5);
        kilk.connectToNodeUndirected(lim, 10);
        lim.connectToNodeUndirected(wex, 20);
        carl.connectToNodeUndirected(wex, 15);
        wex.connectToNodeUndirected(cor, 20);


        //Waterford/Carlow/Cork = 35
        wat.connectToNodeUndirected(carl, 15);
        carl.connectToNodeUndirected(cor, 20);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        waterford = null;
        kilkenny = null;
        carlow = null;
        wexford = null;
        cork = null;
        limerick = null;
        wat = null;
        kilk = null;
        carl = null;
        wex = null;
        cor = null;
        lim = null;
    }

    @org.junit.jupiter.api.Test
    public void DijkstraTest() {

        //assertEquals test should return the cheapest path 2nd route as its shorter
        assertEquals(35, Controller.findCheapestPathDijkstra(wat, cor.data).getPathCost()); // route 2
        assertNotEquals(50, Controller.findCheapestPathDijkstra(wat, cor.data).getPathCost()); // route 1

    }

    @org.junit.jupiter.api.Test
    public void BFSTest() {
        assertEquals(3, Controller.findPathBreadthFirst(wat, cor.data).size());
        assertNotEquals(5, Controller.findPathBreadthFirst(wat, cor.data).size());

    }

    @org.junit.jupiter.api.Test
    public void DFSTest() {
        List<GraphNodeAL2<?>> encountered = new ArrayList<>();
        assertEquals(4, Controller.findAllPathsDepthFirst(wat, encountered, cor.data).size());

/*      Paths
        1,2,6,4,3,5 -- waterford kilkenny limerick wexford carlow cork
        1,2,6,4,5 -- waterford kilkenny limerick wexford cork
        1,3,4,5 -- waterford carlow wexford cork
        1,3,5 -- waterford carlow cork*/

        assertNotEquals(2, Controller.findAllPathsDepthFirst(wat, encountered, cor.data).size());
    }
}


// grade  =