package dijkstraalgorithim;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Class: HubMapRunner
 * Author: Sai Thupakula
 */
public class HubMapRunner 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        // Launches the GUI for selecting a hub network and computing paths
        HubFrame startFrame = new HubFrame();
        startFrame.setVisible(true);
    }
}