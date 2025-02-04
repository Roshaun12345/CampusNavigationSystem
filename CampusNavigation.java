/**
* CampusNavigation
* Reads a file from command line containing node and edges data, then uses the Graph, Node and Edge class to create a graph representation and carry out various graph based features.
*
* @author Roshaun Gregory
* @github ID Roshaun12345
* @version December 7, 2024
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
* Represents a building in a school map.
* Manages building information.
*/
class Node{
    String name;

    Node(String s){
        this.name = s;
    }
}


/**
* Represents the distance between two buildings.
* Manages the start and ending points, as well as time taken to travel distance.
*/
class Edge implements Comparable<Edge>{
    Node start;
    Node end;
    int distance;

    Edge(Node s, Node e, int val){
        this.start = s;
        this.end = e;
        this.distance = val;
    }

   /**
   * Compares the distance of edges.
   *
   * @param nextEdge the other edge that were comparing the current edge to 
   * @return the calculated GPA as a double
   */
    @Override
    public int compareTo(Edge nextEdge) {
        return Integer.compare(this.distance, nextEdge.distance);
    }
}


/**
* Used to organize the nodes and edges into a graph format.
* Allows differing operations to be carried out using the graph data.
*/
class Graph{
    ArrayList<Node> buildings;
    ArrayList<Edge> edges;
    Map<Node, Set<Node>> adjMap;

    Graph(ArrayList<Node> n, ArrayList<Edge> e){
        this.buildings = n;
        this.edges = e;
        this.adjMap = createAdjMap(buildings, edges);
    }

    /**
    * Creates an adjacency map representation of the graph structure.
    * @param nodes an array list of the nodes of the graph
    * @param Edges an array list of the edges of the graph
    * @return the adjacency map
    */
    public Map<Node, Set<Node>> createAdjMap(ArrayList<Node> nodes, ArrayList<Edge> Edges) {
        Map<Node, Set<Node>> adjMap = new HashMap<>();
        int num = nodes.size();
        for (int i = 0; i<num; i++) {
            Node n = nodes.get(i);
            adjMap.put(n, new HashSet<>());
        }

        for (Edge e : Edges) {
            adjMap.get(e.start).add(e.end);
            adjMap.get(e.end).add(e.start);  
        }
        return adjMap;
    }


    /**
    * Displays the graph buildings using depth first search.
    * @param start the starting point given by the user
    */
    public void dfs(Node start){
        Set<Node> visited = new HashSet<>();
        dfs_Rec(start, visited);
    }

    /**
    * Recursive method used for displaying depth first search.
    * @param n the current node position in the graph structure
    * @param v a set containing the nodes that have already been visited in the order they were found
    */
    public void dfs_Rec(Node n, Set<Node> v){
        if(v.contains(n) || n == null){
            return;
        }

        v.add(n);
        System.out.print(n.name+" ");
        Set<Node> neighbors = adjMap.get(n);
        for (Node neighbor : neighbors) {
            if(!v.contains(neighbor)){
               dfs_Rec(neighbor, v);
            }
        }
    }

    
    
    /**
    * Displays the graph buildings using breadth first search.
    * @param start the starting point given by the user
    */
    public void bfs(Node start){
        Set<Node>visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        System.out.print(start.name+" ");
        bfs_Rec(queue, visited);
    }

    /**
    * Recursive method used for displaying breadth first search.
    * @param q a queue containing the nodes that need to be visited
    * @param v a set containing the nodes that have already been visited in the order they were found
    */
    public void bfs_Rec(Queue<Node> q , Set<Node> v){
        if(q.isEmpty()){
            return;
        }

        Node n = q.poll();
        Set<Node> neighbors = adjMap.get(n);
        for(Node neighor : neighbors){
            if(!v.contains(neighor)){
                v.add(neighor);
                q.add(neighor);
                System.out.print(neighor.name+" ");
            }
        }
        bfs_Rec(q, v);
    }

    /**
    * Used to find the minimum spanning tree using kruskals algorithm.
    */
    public void Kruskal(){
        //Sorts the edges in ascending order
        edges.sort(null);
        ArrayList<ArrayList<Node>> sets = new ArrayList<>();
        for(Node node : buildings){
            ArrayList<Node> set = new ArrayList<>();
            set.add(node);
            sets.add(set);
        }

        ArrayList<Edge> mst = new ArrayList<>();
        rec_Kruskal(sets, edges, 0, mst);

        //Displays results
        System.out.println("Minimum Spanning Tree Results:");
        for (Edge edge : mst) {
            System.out.println(edge.start.name + " -> " + edge.end.name + "("+edge.distance+" minutes)");
        }
    }

    /**
    * Recursive method used for getting the minimum spanning tree.
    * @param sets an array list of array lists that contains the disjoint sets of nodes created during the sequence
    * @param edges the sorted list of edges that will be used to find the available minimum distance
    * @param index the index of the current edge in the edge list being looked at.
    * @param mst a list storing the edges that will make up the minimum spanning tree
    */
    public void rec_Kruskal(ArrayList<ArrayList<Node>> sets, ArrayList<Edge> edges, int index, List<Edge> mst ){
        if(index == edges.size()){
            return;
        }

        //Gets the two nodes of an edge
        Edge edge = edges.get(index);
        Node n_start = edge.start;
        Node n_end = edge.end;

       //Gets the respective sets of the two nodes
        ArrayList<Node> setStart = getSet(sets, n_start);
        ArrayList<Node> setEnd = getSet(sets, n_end);

       //Checks if they are in different sets or not
        if (setStart != setEnd) {
           //If not then the two sets will be merged into one
            mst.add(edge);
            setStart.addAll(setEnd);  
            sets.remove(setEnd); 
        }

        index = index + 1;
        rec_Kruskal(sets, edges, index, mst); 
    }


    /**
    * Used to find which set a node belongs to, or if it is part of a set yet.
    * @param sets an array list of array lists that contains the disjoint sets of nodes created during the sequence
    * @param node the node whose set currently needs to be found
    * @return the set it belongs to if found, otherwise it returns null
    */
    public ArrayList<Node> getSet(ArrayList<ArrayList<Node>> sets, Node node) {
        for (ArrayList<Node> set : sets) {
            if (set.contains(node)) {
                return set;
            }
        }
        return null;  
    }


    
    /**
    * Checks if all the edges contains buildings that were given in the file.
    @return true if all edges contain valid buildings, false otherwise
    */
    public boolean validate_buildings(){
        Boolean valid = true;
        for(Edge e : edges){
            if(!buildings.contains(e.start) || !buildings.contains(e.end)){
                valid = false;      
            }
        }
        return valid;

    }

    /**
    * Checks if all edge distances are in the range of 0 to 30 minutes.
    @return true if all edges are within the specified distance, false otherwise
    */
    public boolean validate_distance(){
        Boolean valid = true;
        for(Edge e : edges){
            if(e.distance > 30 || e.distance<0){
                valid = false;      
            }
        }
        return valid;
    }
}


/**
* Reads the inputed file and calls on the Graph class.
* Acts as the user interface.
*/
public class CampusNavigation {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Error, please enter a single file to run next time");
            System.exit(1);
        }
        String file = args[0];
        ArrayList<Edge> edgelist = new ArrayList<Edge>();
        ArrayList<Node> nodelist = new ArrayList<Node>();

        //Reads the file and organizes the information necessary to build the graph structure
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();

            //Reads and creates the node objects
            line = br.readLine();
            while(line != null && !line.trim().equals("EDGES")){
                Node n = new Node(line.trim());
                nodelist.add(n);
                line = br.readLine();
            }

            //Reads and creates the edge objects
            line = br.readLine();
            while(line != null){
                String[] data = line.split(",");
                Node n1 = null;
                Node n2 = null;

                for(int i = 0; i<nodelist.size(); i++){
                    Node n0 = nodelist.get(i); 
                    if(n0 != null && n0.name.equals(data[0])){
                        n1 = n0;
                    }
                    else if(n0 != null && n0.name.equals(data[1])){
                        n2 = n0;
                    }   
                }
                int distance = Integer.parseInt(data[2].trim());
                Edge e = new Edge(n1, n2, distance);
                edgelist.add(e);
                line = br.readLine();
            }
               
        }catch(IOException e){
            System.out.println("Error occured while reading file");
            System.exit(0);
        }catch(NumberFormatException e){
            System.out.println("Error occured while reading file(NumberFormat error)");
            System.exit(0);
        }

        //Creates graph object
        Graph G = new Graph(nodelist, edgelist);

        //Checks if the nodes and edges are logically correct
        if(!G.validate_buildings() || !G.validate_distance()){
            System.out.println("Issue found with edges/weights");
            System.exit(0);
        }

        Scanner scan = new Scanner(System.in);
        String input = "";
        String menu = "Type one of the following to choose an algorithm: \n"+
        "1.) DFS - To Do Depth First Search\n"+
        "2.) BFS -To Do Breadth First Search\n"+
        "3.) MST - To Do Kruskal's Minimum Spanning Tree\n"+
        "4.) Exit - To exit the program\n";

        System.out.println("Welcome to Campus Navigator!");
        System.out.println("Loaded campus map with "+ G.buildings.size()+" buildings and "+G.edges.size()+" paths.");
        System.out.println(menu);

        while(true){
            input = scan.nextLine();
            input = input.trim();
            
            //Used to acess DFS function
            if(input.equals("DFS")){
                Node start = null;
                Boolean found = false;
                System.out.println("Enter starting building");
                input = scan.nextLine();
                input = input.trim();

                //Checks if the user entered an existing building
                for(Node n : nodelist){
                    if(n.name.equals(input)){
                        found = true;
                        start = n;
                        break;
                    }
                }

                if(found){
                    G.dfs(start);
                }else{
                    System.out.println("Building not found");
                    System.out.println(" ");
                }

            //Used to acess DFS function
            }else if (input.equals("BFS")){
                Node start = null;
                Boolean found = false;
                System.out.println("Enter starting building");
                input = scan.nextLine();
                input = input.trim();

                //Checks if the user entered an existing building
                for(Node n : nodelist){
                    if(n.name.equals(input)){
                        found = true;
                        start = n;
                        break;
                    }
                }

                if(found){
                    G.bfs(start);
                }else{
                    System.out.println("Building not found");
                    System.out.println(" ");
                }

            //Used to acess kruskal's algorithm to display minimum spanning tree
            }else if (input.equals("MST")){
                G.Kruskal();

            //Allows user to exit program
            }else if(input.equals("Exit")){
                scan.close();
                System.exit(0);

            //Checks forinvalid input from user
            }else{
                System.out.println("Invalid input, please try again");
            }
            System.out.println(" ");
        }
    } 
}
