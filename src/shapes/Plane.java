package shapes;

import classes.*;

public class Plane extends Material {
    Vec3 normal;
    float distance;
    Color color;
    float reflectivity;

    public Plane(Vec3 normal, float distance, Color color, float reflectivity) {
        super(color, reflectivity, 0);
        this.normal = normal;
        this.distance = distance;
        this.color = color;
        this.reflectivity = reflectivity;
    }

    public Intersection intersect(Ray ray) {
        float denom = Vec3.dot(normal, ray.direction);
        if (denom > 1e-6) {
            Vec3 p0l0 = Vec3.sub(ray.origin, normal.mul(distance));
            float t = Vec3.dot(p0l0, normal) / denom;
            if (t >= 0) {
                Vec3 position = ray.origin.add(ray.direction.mul(t));
                return new Intersection(position, normal, this, reflectivity);
            }
        }
        return null;
    }
}
