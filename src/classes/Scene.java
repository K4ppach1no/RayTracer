package classes;

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
        // This method returns the closest intersection between a ray and an object in the scene.
        // If there is no intersection, it returns null.

        // Initialize closestIntersection to null
        Intersection closestIntersection = null;

        // For each object in the scene
        for (var material : materials) {
            // Get the intersection
            Intersection intersection = material.intersect(ray);

            // If there is an intersection
            if (intersection != null) {
                // If closestIntersection is null or the intersection is closer than closestIntersection
                if (closestIntersection == null || intersection.t < closestIntersection.t) {
                    // Set closestIntersection to intersection
                    closestIntersection = intersection;
                }
            }
        }

        return closestIntersection;
    }
}
