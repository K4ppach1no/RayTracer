package classes;

public class Ray {
    public Vec3 origin;
    public Vec3 direction;
    float t;

    public Ray(Vec3 origin, Vec3 direction, float t) {
        this.origin = origin;
        this.direction = direction;
        this.t = t;
    }


}