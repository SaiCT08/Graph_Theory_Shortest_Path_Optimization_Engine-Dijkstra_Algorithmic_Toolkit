package dijkstraalgorithim;
import java.util.ArrayList;

/**
 * Class: Hub
 * Author: Sai Thupakula
 */
public class Hub implements Comparable<Hub> {
    private String name; // Holds the Hub's name
    private int[] location; // Contains the coordinates: index 0 is x, index 1 is y
    private double distance; // Represents the distance from the Entry Hub
    private ArrayList<Hub> path; // Holds the shortest path from Entry Hub to this Hub
    
    /**
     * Constructor
     * @param n - name of the Hub
     * @param x - x-coordinate
     * @param y - y-coordinate
     * @param d - starting distance from Entry Hub
     */
    public Hub(String n, int x, int y, double d){
        name = n;
        location = new int[]{x, y};
        distance = d;
        path = new ArrayList<Hub>();
    }
    
    /**
     * Sets the distance value
     * @param d - new distance
     * Post Condition: Distance is updated to the given value
     */
    public void setDistance(double d) {
        distance = d;
    }
            
    /**
     * @return Hub's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return Location of the Hub as an array
     */
    public int[] getLocation() {
        return location;
    }
    
    /**
     * @return Distance from the Entry Hub
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * @return Current path to this Hub as an ArrayList
     */
    public ArrayList<Hub> getPath() {
        return path;
    }
    
    /**
     * Updates the path to this Hub
     * @param p - new path as an ArrayList
     */
    public void setPath(ArrayList<Hub> p) {
        path = p;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int compareTo(Hub o) {
        if(name.length() > o.getName().length()) {
            return 1;
        }
        
        if(name.length() < o.getName().length()) {
            return -1;
        }
        return name.compareTo(o.getName());
    }   
}