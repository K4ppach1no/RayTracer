import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import classes.*;
import shapes.Plane;
import shapes.Sphere;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int width = 1280;
        int height = 720;

        // Create a sphere
        Sphere sphere1 = new Sphere(new Vec3(0, 0, -1), 0.5f, new Color(0, 1, 0), 0.5f, 0.5f);

        // Create a light
        Light light1 = new Light(new Vec3(0, 0, 0), new Color(1,1,1), 1.0f);

        // create a plane
        //Plane plane1 = new Plane(new Vec3(0, -1, 0), 1, new Color(1, 1, 1), 0.5f);

        // Create a camera
        Camera camera = new Camera(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 60);

        // add the sphere to the scene
        Scene scene = new Scene(camera, new Sphere[]{sphere1}, new Light[]{light1}, new Plane[]{/*plane1*/});


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