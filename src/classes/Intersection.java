package classes;

public class Intersection {
    public Vec3 position;
    public Vec3 normal;
    public Material material;
    public float t;

    public Intersection(Vec3 position, Vec3 normal, Material material, float t) {
        this.position = position; // the position of the intersection
        this.normal = normal; // the normal of the intersected object
        this.material = material; // the material of the intersected object
        this.t = t; // the distance from the camera to the intersection
    }
}
