import java.util.HashMap;
import java.util.Map;

public class WeightedGraph<V> {
    private final Map<V, Vertex<V>> vertices = new HashMap<>();
    private final boolean directed;

    public WeightedGraph(boolean directed) {
        this.directed = directed;
    }

    public void addEdge(V source, V destination, double weight) {
        Vertex<V> sourceVertex = vertices.computeIfAbsent(source, Vertex::new);
        Vertex<V> destinationVertex = vertices.computeIfAbsent(destination, Vertex::new);

        sourceVertex.addAdjacentVertex(destinationVertex, weight);
        if (!directed) {
            destinationVertex.addAdjacentVertex(sourceVertex, weight);
        }
    }

    public Vertex<V> getVertex(V data) {
        return vertices.get(data);
    }
}