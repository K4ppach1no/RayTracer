import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import classes.*;
import classes.Color;
import classes.Renderer;
import shapes.*;

import static java.lang.System.out;

public class Main {
    static JFrame mainFrame;
    static JSlider sliderX, sliderZ; // Slider for light source
    static JLabel labelX, labelZ;
    static JButton renderButton;

    static float x, z; //
    public static void main(String[] args) {

        // Rendering gui
        mainFrame = new JFrame("Raytracer");
        GridLayout grid = new GridLayout(5,1);

        mainFrame.setLayout(grid);
        sliderX =  new JSlider(JSlider.HORIZONTAL, 0, 10,0);
        sliderZ =  new JSlider(JSlider.HORIZONTAL, 0, 10,0);
        labelX = new JLabel("X: 0");
        labelZ = new JLabel("Z: 0");


        sliderX.setMajorTickSpacing(1);
        sliderX.setPaintTicks(true);
        sliderX.setPaintLabels(true);

        sliderZ.setMajorTickSpacing(1);
        sliderZ.setPaintTicks(true);
        sliderZ.setPaintLabels(true);

        renderButton = new JButton("Render");


        // Listeners for Sliders for changing the X and Z of the light source
        sliderX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelX.setText("X: " + sliderX.getValue());
            }
        });

        sliderZ.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelZ.setText("Z: " + sliderZ.getValue());
            }
        });




        mainFrame.add(labelX);
        mainFrame.add(sliderX);
        mainFrame.add(labelZ);
        mainFrame.add(sliderZ);
        mainFrame.add(renderButton);
        mainFrame.setSize(500, 500);
        mainFrame.setVisible(true);



        renderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {

                x = sliderX.getValue();
                z = sliderZ.getValue();

                long startTime = System.currentTimeMillis();
                
                int width = 300;
                int height = 300;

                // Create a scene
                Scene scene = new Scene();
                
                // Create a ground plane
                scene.addMaterial(new Sphere(
                    new Vec3(0, -100.5f, 0),   // position
                    100,               // radius
                    new Color(140, 157, 183), // color
                    0.5f,                // reflectivity
                    0f                 // transparency
                ));
                
                // Create a sphere
                scene.addMaterial(new Sphere(
                        new Vec3(-0.5f, 0f, -2), // position
                        0.45f, // radius
                        new Color(255, 0, 0), // color
                        0f, // reflectivity
                        0f // transparency
                ));

                // Create a sphere
                scene.addMaterial(new Sphere(
                        new Vec3(0.5f, 0f, -2), // position
                        0.45f, // radius
                        new Color(0, 0, 255), // color
                        0f, // reflectivity
                        0f // transparency
                ));

                // Create a sphere behind the other two
                scene.addMaterial(new Sphere(
                        new Vec3(0, 0f, -3), // position
                        0.5f, // radius
                        new Color(0, 255, 0), // color
                        0f, // reflectivity
                        0.0f // transparency
                ));
                
                // Create a light
                scene.addLight(new Light(
                        new Vec3(x, 0.5f, z), // position
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
                out.println("Time: " + (endTime - startTime) + "ms");
            }
        });
    }
}