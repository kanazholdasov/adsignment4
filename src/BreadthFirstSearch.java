import java.util.*;

public class BreadthFirstSearch<V> implements Search<V> {
    private final Set<V> visited = new HashSet<>();  // Tracks visited vertices
    private final Map<V, V> previousVertices = new HashMap<>();  // Maps each vertex to its predecessor on the path
    private final V startVertex;

    public BreadthFirstSearch(WeightedGraph<V> graph, V startVertex) {
        this.startVertex = startVertex;
        performBFS(graph, startVertex); // Initialize the BFS traversal
    }

    private void performBFS(WeightedGraph<V> graph, V startVertex) {
        Queue<V> queue = new LinkedList<>(); // Queue for BFS traversal
        queue.add(startVertex); // Enqueue the start vertex
        visited.add(startVertex); // Mark the start vertex as visited

        while (!queue.isEmpty()) { // Continue until the queue is empty
            V currentVertex = queue.poll(); // Dequeue the front vertex
            for (Vertex<V> neighbor : graph.getVertex(currentVertex).getAdjacentVertices().keySet()) {
                V neighborData = neighbor.getData(); // Get the data of the neighbor vertex
                if (!visited.contains(neighborData)) { // Check if the neighbor has not been visited
                    previousVertices.put(neighborData, currentVertex); // Record the edge from current to neighbor
                    visited.add(neighborData); // Mark the neighbor as visited
                    queue.add(neighborData); // Enqueue the neighbor for further exploration
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V targetVertex) {
        if (!visited.contains(targetVertex)) return null; // If the target hasn't been visited, return null
        LinkedList<V> path = new LinkedList<>();
        for (V vertex = targetVertex; vertex != null; vertex = previousVertices.get(vertex)) {
            path.addFirst(vertex); // Construct the path by traversing backward from the target using previousVertices map
        }
        return path; // Return the constructed path
    }
}
