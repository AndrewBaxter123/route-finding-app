package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Controller {

    @FXML
    public MenuItem exitApplication;
    public Label pathLabel;
    @FXML
    ImageView pictureView;
    @FXML
    private ToggleGroup method;
    @FXML
    private ListView<String> pathList;
    private ArrayList<GraphNodeAL2> graphNodeCol = new ArrayList<GraphNodeAL2>();
    GraphNodeAL2 startNode, endNode;
    List<List<GraphNodeAL2<?>>> allPaths;

    /**
     * easy exit for the user
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Initialize our room objects created from database file.
     *
     * @throws IOException the io exception
     */
    public void initialize() throws IOException {
        createRoomObjects();
    }

    /**
     * The start node x and y.
     */
    double startNodeX, startNodeY;

    /**
     * Choose start point.
     *
     * @param event the event
     */
    @FXML
    void chooseStart(ActionEvent event) {
        pictureView.setOnMousePressed(e -> {
            ((Pane) pictureView.getParent()).getChildren().removeIf(x -> x instanceof Circle);
            double pictureX, pictureY;
            pictureX = e.getX();
            pictureY = e.getY();

            int index = roomCoords(pictureX, pictureY);
            if (index != -1) {
                startNodeX = e.getX();
                startNodeY = e.getY();
                startNode = graphNodeCol.get(index);

                Circle circle = new Circle(startNodeX, startNodeY, 5, Color.GREEN);
                ((Pane) pictureView.getParent()).getChildren().add(circle);
            }
        });
    }
    /**
     * The end node x and y.
     */
    double endNodeX, endNodeY;

    /**
     * Choose end point.
     *
     * @param event the event
     */
    @FXML
    void chooseEnd(ActionEvent event) {
        pictureView.setOnMousePressed(e -> {
            ((Pane) pictureView.getParent()).getChildren().removeIf(x -> x instanceof Rectangle);
            endNodeX = e.getX();
            endNodeY = e.getY();

            int index = roomCoords(endNodeX, endNodeY);
            if (index != -1) {
                endNode = graphNodeCol.get(index);

                Rectangle rect = new Rectangle(endNodeX, endNodeY, 5, 5);
                ((Pane) pictureView.getParent()).getChildren().add(rect);
                rect.setStroke(Color.BLUE);

            }
        });
    }


    private File database = new File("database.txt"); //finds the file path for the saved cv and saves it again

    /**
     * Create room objects from the database file.
     *
     * @throws IOException the io exception
     */
    public void createRoomObjects() throws IOException {
        String path = "src/sample/database.txt";
        FileReader file = new FileReader(path);
        BufferedReader fileRead = new BufferedReader(file);
        String line = "";
        while ((line = fileRead.readLine()) != null) {

            String[] value = line.split(",");
            String type = value[0];

            if (value[0].equalsIgnoreCase("Room")) {
                String roomName = value[1];
                String roomNumber = value[2];
                String xPos = value[3];
                String yPos = value[4];

                Room newRoom = new Room(roomName, roomNumber, xPos, yPos);
                GraphNodeAL2 graphNode = new GraphNodeAL2(newRoom);
                graphNodeCol.add(graphNode);

            } else {
                String sourceRoom = value[1];
                String destRoom = value[2];

                int cost = Integer.parseInt(value[3]);

                int sourceIndex = roomIndex(sourceRoom);
                int destIndex = roomIndex(destRoom);


                graphNodeCol.get(sourceIndex).connectToNodeUndirected(graphNodeCol.get(destIndex), cost);
//                source.connectToNodeUndirected(source, dest, cost)
            }

        }
        populateSourcePath();
        System.out.println(graphNodeCol.get(0));
        System.out.println(graphNodeCol.get(1));//        System.out.println(graphNodeCol.get(0).adjacencyList.get);
//      System.out.println(graphNodeCol.get(0).getAdjacencyList().get(1).cost);
        System.out.println(graphNodeCol.get(0).data);
    }

    /**
     * Make the path with the chosen algorithm.
     *
     * @param event the event
     */
    @FXML
    void makePath(ActionEvent event) {
        ((Pane) pictureView.getParent()).getChildren().removeIf(x -> x instanceof Line);
        RadioButton rb = (RadioButton) method.getSelectedToggle();
        switch(rb.getText()) {
            case "Dijkstra’s":
                Dijkstra();
                break;
            case "BFS":
                BFS();
                break;
            case "DFS":
                DFS();
                break;
        }
    }

    /**
     * Populate source path.
     */
    public void populateSourcePath() {
        if (graphNodeCol != null) {
            //graphNodeCol.clear(); // should fix the duplicating problem.
            for (GraphNodeAL2 r : graphNodeCol) { // using the iterator
                String[] value =r.data.toString().split(",");
                String roomNum = value[1];
            }
        }
    }


    /**
     * gets the room index.
     *
     * @param id the id
     * @return the int
     */
    public int roomIndex(String id) {
        for (int i = 0; i < graphNodeCol.size(); i += 1) {
            GraphNodeAL2<Room> room = graphNodeCol.get(i);
            String roomNum = room.getData().getRoomNumber();
            if (id.equalsIgnoreCase(roomNum)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * gets the room coordinates for the start and end point.
     *
     * @param x the x
     * @param y the y
     * @return the int
     */
    public int roomCoords(double x, double y) {
        for (int i = 0; i < graphNodeCol.size(); i += 1) {
            GraphNodeAL2<Room> room = graphNodeCol.get(i);
            double roomX = Double.parseDouble(room.getData().getX());
            double roomY = Double.parseDouble(room.getData().getY());
            if ((Math.abs(roomX - x) <= 10)
                    && (Math.abs(roomY - y) <= 10)) {
                return i;
            }
        }
        return -1;
    }

//    /**
//     * Search room number graph node al 2.
//     *
//     * @param id the id
//     * @return the graph node al 2
//     */
//    public GraphNodeAL2 searchRoomNumber(String id) {
//        for (int i = 0; i < graphNodeCol.size(); i += 1) {
//            GraphNodeAL2<Room> room = graphNodeCol.get(i);
//
//            if (id.equalsIgnoreCase(room.getData().getRoomNumber())) {
//                return room;
//            }
//        }
//
//        return null;
//    }


    /**
     * Finding the shortest route by the cheapest path by link cost.
     *
     * @param <T>        the type parameter
     * @param startNode  the start node
     * @param lookingfor the lookingfor
     * @return the costed path
     */
    public static <T> CostedPath findCheapestPathDijkstra(GraphNodeAL2<?> startNode, T lookingfor){
        CostedPath cp=new CostedPath(); //Create result object for cheapest path
        List<GraphNodeAL2<?>> encountered=new ArrayList<>(), unencountered=new ArrayList<>(); //Create encountered/unencountered lists
        startNode.nodeValue=0; //Set the starting node value to zero
        unencountered.add(startNode); //Add the start node as the only value in the unencountered list to start
        GraphNodeAL2<?> currentNode;
        do{ //Loop until unencountered list is empty
            currentNode=unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)
            encountered.add(currentNode); //Record current node in encountered list
            if(currentNode.data.equals(lookingfor)){ //Found goal - assemble path list back to start and return it
                cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
                cp.pathCost=currentNode.nodeValue; //The total cheapest path cost is the node value of the current/goal node
                while(currentNode!=startNode) { //While we're not back to the start node...
                    boolean foundPrevPathNode=false; //Use a flag to identify when the previous path node is identified
                    for(GraphNodeAL2<?> n : encountered) { //For each node in the encountered list...
                        for(GraphLinkAL e : n.adjacencyList) //For each edge from that node...
                            if(e.destNode==currentNode && currentNode.nodeValue-e.cost==n.nodeValue){ //If that edge links to the current node and the difference in node values is the cost of the edge -> found path node!
                                cp.pathList.add(0,n); //Add the identified path node to the front of the result list
                                currentNode=n; //Move the currentNode reference back to the identified path node
                                foundPrevPathNode=true; //Set the flag to break the outer loop
                                break; //We've found the correct previous path node and moved the currentNode reference back to it so break the inner loop
                            }
                        if(foundPrevPathNode) break; //We've identified the previous path node, so break the inner loop to continue
                    }
                }
//Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
                for(GraphNodeAL2<?> n : encountered) n.nodeValue=Integer.MAX_VALUE;
                for(GraphNodeAL2<?> n : unencountered) n.nodeValue=Integer.MAX_VALUE;
                return cp; //The costed (cheapest) path has been assembled, so return it!
            }
//We're not at the goal node yet, so...
            for(GraphLinkAL e : currentNode.adjacencyList) //For each edge/link from the current node...
                if(!encountered.contains(e.destNode)) { //If the node it leads to has not yet been encountered (i.e. processed)
                    e.destNode.nodeValue=Integer.min(e.destNode.nodeValue, currentNode.nodeValue+e.cost); //Update the node value at the end
                    //of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
                    unencountered.add(e.destNode);
                }
            Collections.sort(unencountered,(n1, n2)->n1.nodeValue-n2.nodeValue); //Sort in ascending node value order
        }while(!unencountered.isEmpty());
        return null; //No path found, so return null
    }

    /**
     * Interface method to allow just the starting node and the goal node data to match to be specified
     * single shortest path by number of edges
     *
     * @param <T>        the type parameter
     * @param startNode  the start node
     * @param lookingfor the lookingfor
     * @return the list
     */

    public static <T> List<GraphNodeAL2<?>> findPathBreadthFirst(GraphNodeAL2<?> startNode, T lookingfor){
        List<List<GraphNodeAL2<?>>> agenda=new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNodeAL2<?>> firstAgendaPath=new ArrayList<>(),resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath=findPathBreadthFirst(agenda,null,lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    /**
     * Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
     *
     * @param <T>         the type parameter
     * @param agenda      the agenda
     * @param encountered the encountered
     * @param lookingfor  the lookingfor
     * @return the list
     */

    public static <T> List<GraphNodeAL2<?>> findPathBreadthFirst(List<List<GraphNodeAL2<?>>> agenda, List<GraphNodeAL2<?>> encountered, T lookingfor){
        if(agenda.isEmpty()) return null; //Search failed
        List<GraphNodeAL2<?>> nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL2<?> currentNode=nextPath.get(0); //The first item in the next path is the current node
        if(currentNode.data.equals(lookingfor)) return nextPath; //If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(GraphLinkAL adjLink : currentNode.adjacencyList) //For each adjacent node // CHANGED!
            if(!encountered.contains(adjLink.destNode)) { //If it hasn't already been encountered // added in destNode was the issue with limiting at 10
                List<GraphNodeAL2<?>> newPath=new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
                newPath.add(0,adjLink.destNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda,encountered,lookingfor); //Tail call
    }

    /**
     * finds all the possible routes from chosen start point to end point .
     *
     * @param <T>         the type parameter
     * @param from        the from
     * @param encountered the encountered
     * @param lookingfor  the lookingfor
     * @return the list
     */
//Recursive depth-first search of graph (all paths identified returned)
    public static <T> List<List<GraphNodeAL2<?>>> findAllPathsDepthFirst(GraphNodeAL2<?> from, List<GraphNodeAL2<?>> encountered, T lookingfor){
        List<List<GraphNodeAL2<?>>> result=null, temp2;

        if(from.data.equals(lookingfor)) { //Found it
            List<GraphNodeAL2<?>> temp=new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result=new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list
        for(GraphLinkAL adjLink : from.adjacencyList){
            if(!encountered.contains(adjLink.destNode)) {
                temp2=findAllPathsDepthFirst(adjLink.destNode,new ArrayList<>(encountered),lookingfor); //Use clone of encountered list
//for recursive call!
                if(temp2!=null) { //Result of the recursive call contains one or more paths to the solution node
                    for(List<GraphNodeAL2<?>> x : temp2) //For each partial path list returned
                        x.add(0,from); //Add the current node to the front of each path list
                    if(result==null) result=temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }

    /**
     * calling algorithms and drawing Dijkstra path.
     */
    public void Dijkstra() {
        CostedPath cheapestPath;
        cheapestPath = findCheapestPathDijkstra(startNode, endNode.data);
        double startX, startY;
        startX = startNodeX;
        startY = startNodeY;

        for (GraphNodeAL2<?> n : cheapestPath.pathList) {

            String[] value = n.data.toString().split(",");
            System.out.println(n.data);

            if (!cheapestPath.pathList.contains("34")) {

                double currentX, currentY;
                currentX = Double.parseDouble(value[2]) + pictureView.getLayoutX();
                currentY = Double.parseDouble(value[3]) + pictureView.getLayoutY();

                Line line = new Line(startX, startY, currentX, currentY);
                ((Pane) pictureView.getParent()).getChildren().add(line);

                startX = Double.parseDouble(value[2]);
                startY = Double.parseDouble(value[3]) + pictureView.getLayoutY();
            }
            pathLabel.setText("Total path cost: " + cheapestPath.pathCost);
        }
    }

    /**
     * calling algorithms and drawing BFS path.
     */
//Limit of 11 nodes?
    public void BFS() {
        List<GraphNodeAL2<?>> bfsPath = null;
        bfsPath = findPathBreadthFirst(startNode, endNode.data);
        double startX, startY;
        startX = startNodeX;
        startY = startNodeY;

        for (GraphNodeAL2<?> n : bfsPath) {
            String[] value = n.data.toString().split(",");
            System.out.println(n.data);


            double currentX, currentY;
            currentX = Double.parseDouble(value[2]) + pictureView.getLayoutX();
            currentY = Double.parseDouble(value[3]) + pictureView.getLayoutY();

            Line line = new Line(startX, startY, currentX, currentY);
            ((Pane) pictureView.getParent()).getChildren().add(line);

            startX = Double.parseDouble(value[2]);
            startY = Double.parseDouble(value[3]) + pictureView.getLayoutY();
        }
        pathLabel.setText("Number of nodes: " + bfsPath.size());
    }


    /**
     * calling algorithms and producing all the paths and putting them into list view
     */
    public void DFS() {
        pathList.getItems().clear();
        List<GraphNodeAL2<?>> encountered=new ArrayList<>();
        allPaths = findAllPathsDepthFirst(startNode, encountered, endNode.data );
        System.out.println("");
//        for(GraphNodeAL2<?> n : Collections.min(allPaths,(p1,p2)->p1.size()-p2.size()) ) System.out.println(n.data);
        int pCount=1;

        for (List<GraphNodeAL2<?>> p : allPaths) {
            String pathRooms = "";
            for (GraphNodeAL2<?> n : p) {
                String[] value =n.data.toString().split(",");
                pathRooms = pathRooms + value[1] + ", ";
            }
            pathList.getItems().add("Path " + pCount + ": " + pathRooms);
            pCount++;

        }
        pathLabel.setText("Number of paths: " + allPaths.size());
    }

    /**
     * Selected path button.
     *
     * @param event the event
     */
    @FXML
    void selectedPathButton(ActionEvent event) {
        ((Pane) pictureView.getParent()).getChildren().removeIf(x -> x instanceof Line);
        int index = pathList.getSelectionModel().getSelectedIndex();
        List<GraphNodeAL2<?>> path = allPaths.get(index);

        double startX, startY;
        startX = startNodeX;
        startY = startNodeY;
        for (GraphNodeAL2<?> n : path) {
            String[] value = n.data.toString().split(",");

            double currentX, currentY;
            currentX = Double.parseDouble(value[2]) + pictureView.getLayoutX();
            currentY = Double.parseDouble(value[3]) + pictureView.getLayoutY();

            Line line = new Line(startX, startY, currentX, currentY);
            ((Pane) pictureView.getParent()).getChildren().add(line);

            startX = Double.parseDouble(value[2]);
            startY = Double.parseDouble(value[3]) + pictureView.getLayoutY();
        }
        pathLabel.setText("Nodes in path: " + path.size());
    }

}
