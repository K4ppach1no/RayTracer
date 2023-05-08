package classes;

public class Ray {
    // the origin of the ray
    public Vec3 origin;
    // the direction of the ray
    public Vec3 direction;
    // the maximum distance of the ray
    public float t;

    // constructor
    public Ray(Vec3 origin, Vec3 direction, float t) {
        this.origin = origin;
        this.direction = direction.normalize();
        this.t = t;
    }

    // this method returns the point at the given distance
    public Vec3 pointAt(float t) {
        return origin.add(direction.scale(t));
    }
}