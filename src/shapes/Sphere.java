package shapes;

import classes.*;

public class Sphere extends Material {
    Vec3 center;
    float radius;
    Color color;
    float reflectivity;
    float transparency;

    public Sphere(Vec3 center, float radius, Color color, float reflectivity, float transparency) {
        super(color, reflectivity, transparency);
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.reflectivity = reflectivity;
        this.transparency = transparency;
    }

    public Intersection intersect(Ray ray) {
        float t = Vec3.dot(center.sub(ray.origin), ray.direction);
        Vec3 q = ray.origin.add(ray.direction.mul(t));

        float y = center.sub(q).length();
        if (y > radius) {
            return null;
        }
        float x = (float) Math.sqrt(radius * radius - y * y);
        // t0 is the distance from the camera to the first intersection
        float t0 = t - x;
        float t1 = t + x;
        if (t0 > t1) {
            float temp = t0;
            t0 = t1;
            t1 = temp;
        }
        if (t0 < 0) {
            t0 = t1;
            if (t0 < 0) {
                return null;
            }
        }
        Vec3 position = ray.origin.add(ray.direction.mul(t0));
        Vec3 normal = position.sub(center).normalize();
        return new Intersection(position, normal, this, t0);
    }
}