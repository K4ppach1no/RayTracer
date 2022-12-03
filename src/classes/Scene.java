package classes;

import shapes.Plane;
import shapes.Sphere;

public class Scene {
    // The scene class is a container for all the objects in the scene.
    // It also contains the camera and the lights.

    public Camera camera;
    public Sphere[] spheres;
    public Light[] lights;
    public Plane[] planes;
    public Color backgroundColor;

    public Scene(Camera camera, Sphere[] spheres, Light[] lights, Plane[] planes) {
        this.camera = camera;
        this.spheres = spheres;
        this.lights = lights;
        this.planes = planes;
        this.backgroundColor = new Color(0, 0, 0);
    }

    public Intersection getIntersection(Ray ray) {
        Intersection closestIntersection = null;
        for (Sphere sphere : spheres) {
            Intersection intersection = sphere.intersect(ray);
            if (intersection != null && (closestIntersection == null || intersection.t < closestIntersection.t)) {
                closestIntersection = intersection;
            }
        }
        for (Plane plane : planes) {
            Intersection intersection = plane.intersect(ray);
            if (intersection != null && (closestIntersection == null || intersection.t < closestIntersection.t)) {
                closestIntersection = intersection;
            }
        }
        return closestIntersection;
    }
}
