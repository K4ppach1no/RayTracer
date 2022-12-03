package classes;

public class Intersection {
    public Vec3 position;
    public Vec3 normal;
    public Material material;
    public float t;

    public Intersection(Vec3 position, Vec3 normal, Material material, float t) {
        this.position = position;
        this.normal = normal;
        this.material = material;
        this.t = t;
    }
}
