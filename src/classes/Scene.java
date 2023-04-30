package classes;

import shapes.*;

public class Scene {
    // The scene class is a container for all the objects in the scene.
    // It also contains the camera and the lights.

    public Camera camera;
    public Light[] lights;
    public Material[] materials;
    public Color backgroundColor;

    public Scene(Camera camera, Light[] lights, Material[] materials) {
        this.camera = camera;
        this.lights = lights;
        this.materials = materials;
        this.backgroundColor = new Color(120, 120, 120);
    }

    public Intersection getIntersection(Ray ray) {
        Intersection closestIntersection = null;
        for (Material material : materials) {
            // check if the object is a sphere
            if (material instanceof Sphere) {
                Sphere sphere = (Sphere) material;
                Intersection intersection = sphere.intersect(ray);
                if (intersection != null) {
                    if (closestIntersection == null || intersection.t < closestIntersection.t) {
                        closestIntersection = intersection;
                    }
                }
            }
            // check if the object is a plane
            if (material instanceof Plane) {
                Plane plane = (Plane) material;
                Intersection intersection = plane.intersect(ray);
                if (intersection != null) {
                    if (closestIntersection == null || intersection.t < closestIntersection.t) {
                        closestIntersection = intersection;
                    }
                }
            }
            // check if the object is a Cube
            if (material instanceof Cube) {
                Cube cube = (Cube) material;
                Intersection intersection = cube.intersect(ray);
                if (intersection != null) {
                    if (closestIntersection == null || intersection.t < closestIntersection.t) {
                        closestIntersection = intersection;
                    }
                }
            }
        }
        return closestIntersection;
    }
}
