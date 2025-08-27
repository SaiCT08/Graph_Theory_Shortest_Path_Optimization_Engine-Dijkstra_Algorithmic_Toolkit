package dijkstraalgorithim;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class: HubMap
 * Author: Sai Thupakula
 */
public class HubMap 
{
    private Hub home;  // Starting hub of the network
    private Map<Hub, HashSet<Hub>> connections;  // Adjacency list storing connections between hubs

    /**
     * Creates a HubMap by reading hub data and connections from a file.
     *
     * @param data  Filename containing the hub network data.
     * @param start Name of the starting hub.
     * @throws FileNotFoundException if the file is not found.
     */
    public HubMap(String data, String start) throws FileNotFoundException 
    {
        connections = new HashMap<>();
        Map<String, Hub> hubLookup = new HashMap<>();

        Scanner scanner = new Scanner(new File(data));
        int numHubs = Integer.parseInt(scanner.nextLine().trim());

        // Create and store each hub from the file into a lookup map
        for (int i = 0; i < numHubs; i++) 
        {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            String name = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            Hub hub = new Hub(name, x, y, Double.MAX_VALUE);
            hubLookup.put(name, hub);
            connections.put(hub, new HashSet<>());
        }

        // Read and create connections between hubs
        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] edge = line.split("-");
            Hub a = hubLookup.get(edge[0]);
            Hub b = hubLookup.get(edge[1]);

            if (a != null && b != null) 
            {
                connections.get(a).add(b);
                connections.get(b).add(a);
            }
        }
        scanner.close();

        // Set up the starting hub and begin pathfinding
        home = hubLookup.get(start);
        home.setDistance(0);
        ArrayList<Hub> initialPath = new ArrayList<>();
        initialPath.add(home);
        home.setPath(initialPath);
        
        buildPaths(home, new ArrayList<>(), new ArrayList<>(List.of(home)));
    }

    /**
     * Builds shortest paths from the start hub to all others using Dijkstra's algorithm.
     *
     * @param start        Currently visited hub.
     * @param haveBeen     List of visited hubs.
     * @param stillToVisit List of hubs pending exploration.
     */
    private void buildPaths(Hub start, ArrayList<Hub> haveBeen, ArrayList<Hub> stillToVisit) 
    {
        while (!stillToVisit.isEmpty()) 
        {
            Hub current = null;
            double minDist = Double.MAX_VALUE;
            
            // Find the hub with the smallest known distance so far
            for (Hub hub : stillToVisit) {
                if (hub.getDistance() < minDist) 
                {
                    minDist = hub.getDistance();
                    current = hub;
                }
            }

            if (current == null) return;

            stillToVisit.remove(current);
            haveBeen.add(current);

            // Explore each neighboring hub
            for (Hub neighbor : connections.getOrDefault(current, new HashSet<>())) 
            {
                if (haveBeen.contains(neighbor)) continue;

                int[] currLoc = current.getLocation();
                int[] neighLoc = neighbor.getLocation();
                double dx = currLoc[0] - neighLoc[0];
                double dy = currLoc[1] - neighLoc[1];
                double distance = current.getDistance() + Math.sqrt(dx * dx + dy * dy);

                // If a shorter path is found, update the path and distance
                if (distance < neighbor.getDistance()) 
                {
                    neighbor.setDistance(distance);
                    ArrayList<Hub> newPath = new ArrayList<>(current.getPath());
                    newPath.add(neighbor);
                    neighbor.setPath(newPath);
                }

                if (!stillToVisit.contains(neighbor))
                {
                    stillToVisit.add(neighbor);
                }
            }
            // Recurse with updated exploration state
            buildPaths(start, haveBeen, stillToVisit);
            return;
        }
    }

    /**
     * Returns the shortest path to a specified hub.
     *
     * @param end Name of the destination hub.
     * @return List representing the shortest path, or null if the hub is not found.
     */
    public ArrayList<Hub> findPath(String end) 
    {
        for (Hub hub : connections.keySet()) 
        {
            if (hub.getName().equals(end)) 
            {
                return hub.getPath();
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return connections.toString();
    }
}