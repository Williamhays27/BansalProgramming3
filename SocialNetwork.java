import java.io.*;
import java.util.*;
import java.lang.*;

class SocialNetwork {
    private Map<Integer, List<Edge>> graph;
    private Map<Integer, Integer> waitDays;
    private Map<Integer, String> studentNames;

    public SocialNetwork() {
        graph = new HashMap<>();
        waitDays = new HashMap<>();
        studentNames = new HashMap<>();
    }

    public void buildGraph(String studentsFile, String networkFile) throws IOException {
        // Load students.csv data
        BufferedReader studentsReader = new BufferedReader(new FileReader(studentsFile));
        String line;

        // Skip the header row in students.csv
        studentsReader.readLine(); // Skip header row

        while ((line = studentsReader.readLine()) != null) {
            String[] parts = line.split(",");

            // Parse enrollment number, name, and wait days
            int id = Integer.parseInt(parts[0].trim()); // Enrollment Number
            String name = parts[1].trim(); // Student Name
            int days = Integer.parseInt(parts[3].trim()); // Number of Wait Days

            // Store data
            waitDays.put(id, days);
            studentNames.put(id, name); // Store the name
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
            for (int i = 3; i <= 7; i++) { // Adjusted to match column positions for connections
                int to = Integer.parseInt(parts[i].trim());
                graph.get(from).add(new Edge(to, waitDays.get(from)));
            }
        }
        networkReader.close();
    }

    

    public List<String> getNetwork(int student) {
        List<Edge> studentNetwork = graph.get(student);
        List<String> networkList = new ArrayList<>();

        // Check if the network is empty
        if (studentNetwork == null || studentNetwork.isEmpty()) {
            System.out.println("Student " + student + " has no connections");
            return networkList;
        }

        // Iterate over edges and include student names
        for (Edge edge : studentNetwork) {
            int connectedStudent = edge.to;
            String connectedStudentName = studentNames.get(connectedStudent);
            networkList.add(connectedStudent + " (" + connectedStudentName + ")");
        }
        return networkList;
    }


    // Using Dijkstra's Algorithm to find the quickest path between 2 nodes.
    public Path findQuickestPath(int start, int end) {
        // Priority queue to store nodes based on their current shortest distance (wait days)
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<Integer, Integer> distances = new HashMap<>(); // Store shortest distances to each node
        Map<Integer, Integer> previous = new HashMap<>(); // Store previous node to reconstruct the path
        Set<Integer> visited = new HashSet<>(); // Track visited nodes
    
        // Initialize distances to infinity for all nodes except the start node
        for (int node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(new Node(start, 0));
    
        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            int current = currentNode.id;
    
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
    
            // If we reach the end node, reconstruct and return the path
            if (current == end) {
                List<Integer> pathNodes = reconstructPath(previous, start, end);
                int totalDays = distances.get(end);
                return new Path(pathNodes, totalDays);
            }
    
            // Relaxation process for neighbors
            List<Edge> neighbors = graph.get(current);
            if (neighbors != null) {
                for (Edge edge : neighbors) {
                    int neighbor = edge.to;
                    int newDistance = distances.get(current) + edge.weight;
    
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                        priorityQueue.add(new Node(neighbor, newDistance));
                    }
                }
            }
        }
    
        // If no path found, return a Path object with an empty list and distance -1
        return new Path(new ArrayList<>(), -1);
    }
    
    // Helper method to reconstruct the path from start to end using the previous map
    private List<Integer> reconstructPath(Map<Integer, Integer> previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (Integer at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
    
    // Helper class for nodes in the priority queue
    class Node {
        int id;
        int distance;
    
        public Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
    }
    
    
    
    

    public void discon(int a, int b) {
        List<Edge> edges = graph.get(a);
        if (edges != null) {
            edges.removeIf(edge -> edge.to == b);
        }
    }

    public void iWaitDays(int student) {
        if (waitDays.containsKey(student)) {
            int newDays = waitDays.get(student) + 1;
            waitDays.put(student, newDays);
            updateEdgeWeights(student, newDays);
        }
    }

    public void dWaitDays(int student) {
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