package classes;

public class object3d {
    public Vec3 position;
    public Vec3 rotation;
    public Vec3 scale;
    public Material material;
    public float t;

    public object3d(Vec3 position, Vec3 rotation, Vec3 scale, Material material) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
    }

    public Intersection intersect(Ray ray) {
        return null;
    }

    public Vec3 Normal(Vec3 hitPoint) {
        return null;
    }
}
