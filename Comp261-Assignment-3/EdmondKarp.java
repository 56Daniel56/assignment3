
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Map;
import javafx.util.Pair;

/** Edmond karp algorithm to find augmentation paths and network flow.
 * 
 * This would include building the supporting data structures:
 * 
 * a) Building the residual graph(that includes original and backward (reverse) edges.)
 *     - maintain a map of Edges where for every edge in the original graph we add a reverse edge in the residual graph.
 *     - The map of edges are set to include original edges at even indices and reverse edges at odd indices (this helps accessing the corresponding backward edge easily)
 *     
 *     
 * b) Using this residual graph, for each city maintain a list of edges out of the city (this helps accessing the neighbours of a node (both original and reverse))

 * The class finds : augmentation paths, their corresponing flows and the total flow
 * 
 * 
 */

public class EdmondKarp {
    // class members

    //data structure to maintain a list of forward and reverse edges - forward edges stored at even indices and reverse edges stored at odd indices
    private static Map<String,Edge> edges; 

    // Augmentation path and the corresponding flow
    private static ArrayList<Pair<ArrayList<String>, Integer>> augmentationPaths =null;

    
    //TODO:Build the residual graph that includes original and reverse edges 
    public static void computeResidualGraph(Graph graph){
        // TODO
        // string object of id's
        //for each forward edge, need reverse edge in array list on odds and evens.
        //ArrayList<Edge> edges = new ArrayList<>();
        //printResidualGraphData(graph);  //may help in debugging
        // END TODO

        //for(City c :graph.getCities().values()){
        //}
        edges = new HashMap<>();
        System.out.println("hello");
        int counter = 0;
        for(Edge e :graph.getOriginalEdges()){
            //System.out.println(e.toString());
            edges.put(Integer.toString(counter),e);

            if(e.fromCity()!= null){
                e.fromCity().addEdgeId(Integer.toString(counter));
                e.toCity().addFromLinks(e.fromCity());
            }
            if(e.fromCity()!= null){
                e.toCity().addEdgeId(Integer.toString(counter));
                e.fromCity().addToLinks(e.toCity());
            }
            
            
            counter++;
            Edge reverse = new Edge(e.toCity(), e.fromCity(), e.transpType(), e.capacity(), e.flow());
            if(e.fromCity()!= null){
                e.fromCity().addEdgeId(Integer.toString(counter));
                e.fromCity().addFromLinks(e.toCity());
            }
            if(e.fromCity()!= null){
                e.toCity().addEdgeId(Integer.toString(counter));
                e.toCity().addToLinks(e.fromCity());
            }
            edges.put(Integer.toString(counter), reverse);
            counter++;
            //e.toString();
        }



        printResidualGraphData(graph);
        



    }

    // Method to print Residual Graph 
    public static void printResidualGraphData(Graph graph){
        System.out.println("\nResidual Graph");
        System.out.println("\n=============================\nCities:");
        for (City city : graph.getCities().values()){
            System.out.print(city.toString());

            // for each city display the out edges 
            for(String eId: city.getEdgeIds()){
                System.out.print("["+eId+"] ");
            }
            System.out.println();
        }
        System.out.println("\n=============================\nEdges(Original(with even Id) and Reverse(with odd Id):");
        edges.forEach((eId, edge)->
                System.out.println("["+eId+"] " +edge.toString()));

        System.out.println("===============");
    }

    //=============================================================================
    //  Methods to access data from the graph. 
    //=============================================================================
    /**
     * Return the corresonding edge for a given key
     */

    public static Edge getEdge(String id){
        return edges.get(id);
    }

    /** find maximum flow
     * 
     */
    // TODO: Find augmentation paths and their corresponding flows
    public static ArrayList<Pair<ArrayList<String>, Integer>> calcMaxflows(Graph graph, City from, City to) {
        //TODO

        //need to return array list of stops, combined with the flow bottleneck as integer
        //in a array list.
        augmentationPaths = new ArrayList<Pair<ArrayList<String>, Integer>>();
        int flow = 0;
        int maxFlow = 0;

        //Map<String, City> cityMap = graph.getCities();
        
        //int value = (int) f.getValue();
        //System.out.println(f.getValue());
        boolean action = true;
        while(action){
            Pair f = bfs(graph,from,to);
            if(f.getValue().equals(0)){
                action = false;
                break;
            }
            ArrayList<String> ids = (ArrayList<String>) f.getKey();
            for(String id : ids){
                Edge currentEdge = getEdge(id);
                flow += currentEdge.flow();

            }
            augmentationPaths.add(new Pair<ArrayList<String>,Integer>(ids, (Integer)f.getValue()));
            //flow = flow+(int)f.getValue();
            //String current = from.getId();
            
            
            
           // while(!current.equals(to.getId())){

            //}
    }

         

        
        // END TODO
        return augmentationPaths;
    }




    //TODO:when augmentation path is found, work out how not to find that path again

    // TODO:Use BFS to find a path from s to t along with the correponding bottleneck flow
    public static Pair<ArrayList<String>, Integer>  bfs(Graph graph, City s, City t) {
        //the integer is the flow in the array list of edges
        ArrayList<String> augmentationPath = new ArrayList<String>();
        //city id, edge id???
        HashMap<String, String> backPointer = new HashMap<String, String>();
        // TODO

        int flow = 0;
        int currentFlow = 0;
        int count = 0;
        Queue<City> queue = new LinkedList<> (); 
        System.out.println(s.toString());
        queue.add(s);

        

        while(!queue.isEmpty()){
            City current = queue.poll();
            ArrayList<String> outGoingEdges = new ArrayList<>();
            //need to get the outgoing cities edges
           // System.out.println("To Links:"+current.getToLinks());

            for(City city : current.getToLinks()){
               // System.out.println("From Links:"+city.getFromLinks());
                //System.out.println("To Links:"+city.getToLinks());
                for(String edgeID : city.getEdgeIds()){
                    
                    for(String currentEdgeIDs : current.getEdgeIds()){
                        if(edgeID.equals(currentEdgeIDs)){
                            outGoingEdges.add(currentEdgeIDs);
                        }
                    }
                }
            }
            //Collection<String> edgeIds = 

            for(String outEdgeID : outGoingEdges){
               

                Edge outEdge = edges.get(outEdgeID);
                System.out.println(outEdge.toString());
            }

            for(String outEdgeID : outGoingEdges){
               

                Edge outEdge = edges.get(outEdgeID);
                if (count == 0){
                    flow = outEdge.capacity();
                    count++;
                }
                currentFlow = Math.min(flow,outEdge.capacity());
                if(!outEdge.toCity().getId().equals(s.getId())&&(backPointer.get(outEdge.toCity().getId())==null)&&(outEdge.capacity()!=0)){
                    backPointer.put(outEdge.toCity().getId(),outEdgeID);
                    if(backPointer.get(t.getId())!=null){
                        String pathEdge = backPointer.get(t.getId());
                        while(pathEdge != null){
                            augmentationPath.add(pathEdge);
                            pathEdge = backPointer.get(edges.get(pathEdge).fromCity().getId());
                        
                        }
                        Collections.reverse(augmentationPath);
                        int minFlow = Integer.MAX_VALUE;
                        for(String eID : augmentationPath){
                            if(minFlow> getEdge(eID).capacity()){
                                minFlow = getEdge(eID).capacity();
                            }
                        }
                        Pair p = new Pair<ArrayList<String>,Integer>(augmentationPath, minFlow);
                        if(augmentationPaths.contains(p)){
                            continue;
                        }
                        System.out.println("path found");
                        return p;
                    }

                    queue.add(outEdge.toCity());

                }
            }


        }

       
        
        // END TODO
        System.out.println("I returned a null value in bfs!");
        return new Pair(null,0);
    }
   
}


