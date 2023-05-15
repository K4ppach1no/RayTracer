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
        // Cube intersection
        // https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection

        float tmin, tmax, tymin, tymax, tzmin, tzmax;
        float divx = 1 / ray.direction.x;
        float divy = 1 / ray.direction.y;
        float divz = 1 / ray.direction.z;

        // if divx is negative, then tmin and tmax are swapped
        if (divx >= 0) {
            tmin = (position.x - ray.origin.x) * divx;
            tmax = ((position.x + size) - ray.origin.x) * divx;
        } else {
            tmin = ((position.x + size) - ray.origin.x) * divx;
            tmax = (position.x - ray.origin.x) * divx;
        }

        // if divy is negative, then tymin and tymax are swapped
        if (divy >= 0) {
            tymin = (position.y - ray.origin.y) * divy;
            tymax = ((position.y + size) - ray.origin.y) * divy;
        } else {
            tymin = ((position.y + size) - ray.origin.y) * divy;
            tymax = (position.y - ray.origin.y) * divy;
        }

        // if tmin is greater than tymax or tmax is less than tymin, then there is no intersection
        if ((tmin > tymax) || (tmax < tymin)) {
            return null;
        }

        // if tymin is greater than tmin, then set tmin to tymin
        if (tymin > tmin) {
            tmin = tymin;
        }

        // if tymax is less than tmax, then set tmax to tymax
        if (tymax < tmax) {
            tmax = tymax;
        }

        // if divz is negative, then tzmin and tzmax are swapped
        if (divz >= 0) {
            tzmin = (position.z - ray.origin.z) * divz;
            tzmax = ((position.z + size) - ray.origin.z) * divz;
        } else {
            tzmin = ((position.z + size) - ray.origin.z) * divz;
            tzmax = (position.z - ray.origin.z) * divz;
        }

        // if tmin is greater than tzmax or tmax is less than tzmin, then there is no intersection
        if ((tmin > tzmax) || (tmax < tzmin)) {
            return null;
        }

        // if tzmin is greater than tmin, then set tmin to tzmin
        if (tzmin > tmin) {
            tmin = tzmin;
        }

        // if tzmax is less than tmax, then set tmax to tzmax
        if (tzmax < tmax) {
            tmax = tzmax;
        }

        // if tmin is greater than 0, then there is an intersection
        if (tmin > 0) {
            Vec3 position = ray.pointAt(tmin);
            Vec3 normal = position.normalize();
            return new Intersection(position, normal, this, tmin);
        }

        return null;
    }
}
