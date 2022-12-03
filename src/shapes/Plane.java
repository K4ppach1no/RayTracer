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
        float t = -(ray.origin.y-distance) / ray.direction.y;
        if (t < 0) {return null;}
        Vec3 position = ray.origin.add(ray.direction.scale(t));
        return new Intersection(position, normal, this, t);
    }
}
