package classes;

public class Ray {
    public Vec3 origin;
    public Vec3 direction;
    public float t;

    public Ray(Vec3 origin, Vec3 direction, float t) {
        this.origin = origin;
        this.direction = direction.normalize();
        this.t = t;
    }

    public Vec3 pointAt(float t) {
        return origin.add(direction.scale(t));
    }


}