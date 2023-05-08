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

    @Override
    public Intersection intersect(Ray ray) {
        Vec3 op = center.sub(ray.origin);
        float t = op.dot(ray.direction);
        Vec3 q = op.sub(ray.direction.scale(t));
        float p2 = q.dot(q);
        if (p2 > radius * radius) {
            return null;
        }
        t -= (float) Math.sqrt(radius * radius - p2);
        if ( t < ray.t && t > 0) {
            Vec3 position = ray.pointAt(t);
            Vec3 normal = position.sub(center).normalize();
            return new Intersection(position, normal, this, t);
        } 
        return null;
    }
}