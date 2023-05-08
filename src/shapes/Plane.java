package shapes;

import classes.*;

public class Plane extends Material {
    Vec3 normal;
    float distance;
    Color color;
    float reflectivity;
    float transparency;

    public Plane(Vec3 normal, float distance, Color color, float reflectivity, float transparency) {
        super(color, reflectivity, transparency);
        this.normal = normal;
        this.distance = distance;
        this.color = color;
        this.reflectivity = reflectivity;
        
    }

    @Override
    public Intersection intersect(Ray ray) {
        float t = -(ray.origin.dot(normal) + distance) / ray.direction.dot(normal);
        if (t < ray.t && t > 0) {
            Vec3 position = ray.pointAt(t);
            return new Intersection(position, normal, this, t);
        }
        return null;
    }
}
