import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import classes.*;
import shapes.*;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int width = 1000;
        int height = 1000;

        // Create a scene
        Scene scene = new Scene();
        
        // Create a plane
        scene.addMaterial(new Plane(
        		new Vec3(0, 1, 1),
        		3f,
        		new Color(255, 255, 255),
        		0.5f,
        		0f
        ));

        // Create a sphere
        scene.addMaterial(new Sphere(
                new Vec3(-0.5f, 0, -2), // position
                0.45f, // radius
                new Color(255, 0, 0), // color
                0f, // reflectivity
                1f // transparency
        ));

        // Create a sphere
        scene.addMaterial(new Sphere(
                new Vec3(0.5f, 0, -2), // position
                0.45f, // radius
                new Color(0, 0, 255), // color
                0f, // reflectivity
                0f // transparency
        ));

        // Create a sphere behind the other two
        scene.addMaterial(new Sphere(
                new Vec3(0, 0.5f, -3), // position
                0.5f, // radius
                new Color(0, 255, 0), // color
                0f, // reflectivity
                0.0f // transparency
        ));

        // Create a light
        scene.addLight(new Light(
                new Vec3(1, 0, 0), // position
                new Color(1, 1, 1), // color
                2, // intensity
                new Vec3(0, 0, 0) // direction
        ));

        File image = new File("image.png");
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Render the scene
        Renderer.render(scene, img);

        try {
            ImageIO.write(img, "png", image);
            
            String os = System.getProperty("os.name").toLowerCase();
            String command;
            
            // open the image
            if (os.contains("win"))
            {
            	// Windows command
            	command = "cmd /c start " + image.getAbsolutePath();
            }
            else if (os.contains("mac"))
            {
            	// MacOS command
            	command = "open " + image.getAbsolutePath();
            }
            else 
            {
            	throw new UnsupportedOperationException("Unsupported OS: " + os);
            }
            
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }
}