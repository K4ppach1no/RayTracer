package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shapes.*;

public class Scene {
    // The scene class is a container for all the objects in the scene.
    // It also contains the camera and the lights.

    public Camera camera;
    public Light[] lights;
    public Material[] materials;

    public Scene(Camera camera, Light[] lights, Material[] materials) {
        this.camera = camera;
        this.lights = lights;
        this.materials = materials;
    }

    public Scene() {
        this.camera = new Camera(new Vec3(0, 0, 0), new Vec3(0, 0, -1), 60);
        this.lights = new Light[0];
        this.materials = new Material[0];
    }

    // add light to the scene
    public void addLight(Light light) {
        Light[] newLights = new Light[lights.length + 1];
        for (int i = 0; i < lights.length; i++) {
            newLights[i] = lights[i];
        }
        newLights[lights.length] = light;
        lights = newLights;
    }

    // add material to the scene
    public void addMaterial(Material material) {
        Material[] newMaterials = new Material[materials.length + 1];
        for (int i = 0; i < materials.length; i++) {
            newMaterials[i] = materials[i];
        }
        newMaterials[materials.length] = material;
        materials = newMaterials;
    }

    // option to add a different camera
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    // get the closest intersection between a ray and an object in the scene
    public Intersection getIntersection(Ray ray) {
        // This method returns the closest intersection between a ray and an object in
        // the scene.
        // If there is no intersection, it returns null.

        // Initialize closestIntersection to null
        Intersection closestIntersection = null;

        // For each object in the scene
        for (var material : materials) {
            // Get the intersection
            Intersection intersection = material.intersect(ray);
            // If there is an intersection
            if (intersection != null) {
                // If closestIntersection is null or the intersection is closer than
                // closestIntersection
                if (closestIntersection == null || intersection.t < closestIntersection.t) {
                    // Set closestIntersection to intersection
                    closestIntersection = intersection;
                }
            }
        }

        return closestIntersection;
    }

    public void loadObj(Scene scene, String fileName, Color color, float reflectivity, float transparency) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        List<Vec3> vertices = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                // Parse vertex position
                String[] parts = line.split("\\s+");
                float x = Float.parseFloat(parts[1]);
                float y = Float.parseFloat(parts[2]);
                float z = Float.parseFloat(parts[3]);
                vertices.add(new Vec3(x, y, z));
            } else if (line.startsWith("f ")) {
                // Parse face
                String[] parts = line.split("\\s+");
                int v1 = Integer.parseInt(parts[1].split("/")[0]) - 1;
                int v2 = Integer.parseInt(parts[2].split("/")[0]) - 1;
                int v3 = Integer.parseInt(parts[3].split("/")[0]) - 1;
                scene.addMaterial(new Triangle(vertices.get(v1), vertices.get(v2), vertices.get(v3), color, reflectivity, transparency));
            }
        }

        reader.close();
    }
}
