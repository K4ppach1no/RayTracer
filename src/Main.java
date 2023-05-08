import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import classes.*;
import shapes.*;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int width = 4096;
        int height = 4096;

        // list of materials
        Vector<Material> materials = new Vector<Material>();

        // Create a sphere
        Sphere sphere1 = new Sphere(
                new Vec3(-0.6f, 0, -2), // position
                0.5f, // radius
                new Color(255, 0, 0), // color
                0f, // reflectivity
                0f // transparency
        );
        materials.add(sphere1);

        // Create a sphere
        Sphere sphere2 = new Sphere(
                new Vec3(0.5f, 0, -2), // position
                0.5f, // radius
                new Color(0, 0, 255), // color
                0f, // reflectivity
                0.0f // transparency
        );
        materials.add(sphere2);

        // Create a sphere behind the other two
        Sphere sphere3 = new Sphere(
                new Vec3(0, 0.5f, -3), // position
                0.5f, // radius
                new Color(0, 255, 0), // color
                0f, // reflectivity
                0.0f // transparency
        );
        materials.add(sphere3);

        // Create a plane
        Plane plane1 = new Plane(
                new Vec3(0, 1, 0), // normal
                -1, // distance
                new Color(255, 255, 255), // color
                0.0f, // reflectivity
                0.0f // transparency
        );
        //materials.add(plane1);

        // Create a light
        Light light1 = new Light(new Vec3(1, 0, 0), new Color(1, 1, 1), 2.0f, new Vec3(0, 0, 0));

        // Create a camera
        Camera camera = new Camera(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 60);

        // add the sphere to the scene
        Scene scene = new Scene(
                camera,
                new Light[] { light1 },
                materials.toArray(new Material[0]));

        File image = new File("image.png");
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Render the scene
        Renderer.render(scene, img);

        try {
            ImageIO.write(img, "png", image);
            // open the image
            Runtime.getRuntime().exec("cmd /c start " + image.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }
}