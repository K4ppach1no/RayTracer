package classes;

public class Intersection {
    // the position of the intersection
    public Vec3 position;
    // the normal of the intersected object
    public Vec3 normal;
    // the material of the intersected object
    public Material material;
    // the distance from the camera to the intersection
    public float t;

    // constructor
    public Intersection(Vec3 position, Vec3 normal, Material material, float t) {
        this.position = position; // the position of the intersection
        this.normal = normal; // the normal of the intersected object
        this.material = material; // the material of the intersected object
        this.t = t; // the distance from the camera to the intersection
    }
}
