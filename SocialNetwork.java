import java.io.*;
import java.util.*;

class SocialNetwork {
    private Map<Integer, List<Edge>> graph;
    private Map<Integer, Integer> waitDays;

    public SocialNetwork() {
        graph = new HashMap<>();
        waitDays = new HashMap<>();
    }

    public void buildGraph(String studentsFile, String networkFile) throws IOException {
        // Load students.csv data
        BufferedReader studentsReader = new BufferedReader(new FileReader(studentsFile));
        String line;
    
        // Skip the header row in students.csv
        studentsReader.readLine(); // Skip header row
    
        while ((line = studentsReader.readLine()) != null) {
            String[] parts = line.split(",");
            
            // Parse enrollment number and wait days, ignore name columns
            int id = Integer.parseInt(parts[0].trim()); // Enrollment Number
            int days = Integer.parseInt(parts[3].trim()); // Number of Wait Days
            
            // Store data
            waitDays.put(id, days);
            graph.put(id, new ArrayList<>());
        }
        studentsReader.close();
    
        // Load network.csv data
        BufferedReader networkReader = new BufferedReader(new FileReader(networkFile));
    
        // Skip the header row in network.csv
        networkReader.readLine(); // Skip header row
    
        while ((line = networkReader.readLine()) != null) {
            String[] parts = line.split(",");
            
            // Parse the enrollment number for the starting node and connections
            int from = Integer.parseInt(parts[0].trim());
            
            // Add connections (Connection 1 to Connection 5)
            for (int i = 3; i <= 7; i++) {  // Adjusted to match column positions for connections
                int to = Integer.parseInt(parts[i].trim());
                graph.get(from).add(new Edge(to, waitDays.get(from)));
            }
        }
        networkReader.close();
    }
    

    public List<Integer> getNetwork(int student) {
        
    }

    public Path findQuickestPath(int start, int end) {
       
    }
    
    
    

    public void disconnect(int a, int b) {
        List<Edge> edges = graph.get(a);
        if (edges != null) {
            edges.removeIf(edge -> edge.to == b);
        }
    }

    public void increaseWaitDays(int student) {
        if (waitDays.containsKey(student)) {
            int newDays = waitDays.get(student) + 1;
            waitDays.put(student, newDays);
            updateEdgeWeights(student, newDays);
        }
    }

    public void decreaseWaitDays(int student) {
        if (waitDays.containsKey(student) && waitDays.get(student) > 0) {
            int newDays = waitDays.get(student) - 1;
            waitDays.put(student, newDays);
            updateEdgeWeights(student, newDays);
        }
    }

    private void updateEdgeWeights(int student, int newWeight) {
        List<Edge> edges = graph.get(student);
        if (edges != null) {
            for (Edge edge : edges) {
                edge.weight = newWeight;
            }
        }
    }

    class Edge {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    class Path {
        List<Integer> nodes;
        int totalDays;
        int nodeCount;

        public Path(List<Integer> nodes, int totalDays) {
            this.nodes = nodes;
            this.totalDays = totalDays;
            this.nodeCount = nodes.size();
        }
    }
}
