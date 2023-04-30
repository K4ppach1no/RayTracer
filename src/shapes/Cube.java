package shapes;

import classes.*;

public class Cube extends Material {
    Vec3 position;
    float size;
    Color color;
    float reflectivity;
    float transparency;

    public Cube(Vec3 position, float size, Color color, float reflectivity, float transparency) {
        super(color, reflectivity, transparency);
        this.position = position;
        this.size = size;
        this.color = color;
        this.reflectivity = reflectivity;
        this.transparency = transparency;
    }

    @Override
    public Intersection intersect(Ray ray) {
        float t = Vec3.dot(position.sub(ray.origin), ray.direction);
        Vec3 q = ray.origin.add(ray.direction.mul(t));

        float y = position.sub(q).length();
        if (y > size) {
            return null;
        }
        float x = (float) Math.sqrt(size * size - y * y);
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
        Vec3 normal = position.sub(position).normalize();
        return new Intersection(position, normal, this, t0);
    }
}
