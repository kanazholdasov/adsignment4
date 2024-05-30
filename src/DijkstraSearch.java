import java.util.*;

public class DijkstraSearch<V> implements Search<V> {
    private Map<V, Double> distances = new HashMap<>();  // Maps each vertex to its shortest distance from the start vertex
    private Map<V, V> previousVertices = new HashMap<>();  // Maps each vertex to its predecessor on the shortest path
    private Set<V> visitedVertices = new HashSet<>();  // Keeps track of visited vertices
    private V startVertex;

    public DijkstraSearch(WeightedGraph<V> graph, V startVertex) {
        this.startVertex = startVertex;
        performDijkstra(graph, startVertex);
    }

    private void performDijkstra(WeightedGraph<V> graph, V startVertex) {
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));  // Priority queue for vertices based on their distances
        distances.put(startVertex, 0.0);  // Initialize distance to the start vertex as 0
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            V currentVertex = priorityQueue.poll();  // Retrieve the vertex with the smallest distance
            visitedVertices.add(currentVertex);  // Mark the current vertex as visited

            for (Map.Entry<Vertex<V>, Double> entry : graph.getVertex(currentVertex).getAdjacentVertices().entrySet()) {
                V neighborVertex = entry.getKey().getData();  // Get the adjacent vertex
                double edgeWeight = entry.getValue();  // Get the weight of the edge to the adjacent vertex

                if (visitedVertices.contains(neighborVertex)) continue;  // Skip the neighbor if it has already been visited

                double newDistance = distances.get(currentVertex) + edgeWeight;  // Calculate the new potential shortest distance to the neighbor

                // If the new distance is shorter, update the distance and predecessor
                if (newDistance < distances.getOrDefault(neighborVertex, Double.POSITIVE_INFINITY)) {
                    distances.put(neighborVertex, newDistance);  // Update the shortest distance to the neighbor
                    previousVertices.put(neighborVertex, currentVertex);  // Update the predecessor of the neighbor
                    priorityQueue.add(neighborVertex);  // Add the neighbor to the priority queue for further exploration
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V targetVertex) {
        if (!distances.containsKey(targetVertex)) return null;  // Return null if there is no path to the target vertex
        LinkedList<V> path = new LinkedList<>();
        for (V vertex = targetVertex; vertex != null; vertex = previousVertices.get(vertex)) {
            path.addFirst(vertex);  // Construct the path in reverse order
        }
        return path;
    }
}
