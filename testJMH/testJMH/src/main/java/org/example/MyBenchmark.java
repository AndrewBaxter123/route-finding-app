package jmh;

import org.example.Controller;
import org.example.GraphNodeAL2;
import org.example.Room;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Measurement(iterations=10)
@Warmup(iterations=5)
@Fork(value=1)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MyBenchmark {

    // how many transactions can be done at one time - throughput
    // average time


    Room waterford, kilkenny, carlow, wexford, cork, limerick;
    GraphNodeAL2<Room> wat, kilk, carl, wex, cor, lim;
    List<GraphNodeAL2<?>> encountered = new ArrayList<>();
    @Setup(Level.Invocation)
    public void setup() {
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

    @Benchmark
    public void DFS() {
        Controller.findAllPathsDepthFirst(wat, encountered, cor.data);
    }

    @Benchmark
    public void Dijkstra() {
        Controller.findCheapestPathDijkstra(wat, cor.data);
    }

    @Benchmark
    public void BFS() {
        Controller.findPathBreadthFirst(wat, cor.data);
    }

    public static void main(String[] args) throws
            RunnerException, IOException {
        Main.main(args);
    }
}
