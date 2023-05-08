package shapes;

import classes.*;

public class Triangle extends Material {

    public static final float EPSILON = 0.0001f;
    Vec3 v1;
    Vec3 v2;
    Vec3 v3;

    public Triangle(Vec3 v1, Vec3 v2, Vec3 v3, Color color, float reflectivity, float transparency) {
        super(color, reflectivity, transparency);
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Vec3 getV1() {
        return v1;
    }

    public Vec3 getV2() {
        return v2;
    }

    public Vec3 getV3() {
        return v3;
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Find the normal of the triangle
        Vec3 edge1 = v2.sub(v1);
        Vec3 edge2 = v3.sub(v1);
        Vec3 normal = edge1.cross(edge2).normalize();

        // Check if the ray and triangle are parallel
        float denominator = normal.dot(ray.direction);
        if (Math.abs(denominator) < EPSILON) {
            return null;
        }

        // Find the point of intersection between the ray and the plane of the triangle
        Vec3 toTriangle = v1.sub(ray.origin);
        float t = toTriangle.dot(normal) / denominator;
        if (t < EPSILON) {
            return null;
        }

        Vec3 intersectionPoint = ray.origin.add(ray.direction.scale(t));

        // Check if the intersection point is inside the triangle
        Vec3 edge1Cross = v1.sub(v3).cross(intersectionPoint.sub(v3));
        if (normal.dot(edge1Cross) < EPSILON) {
            return null;
        }

        Vec3 edge2Cross = edge2.cross(intersectionPoint.sub(v3));
        if (normal.dot(edge2Cross) < EPSILON) {
            return null;
        }

        Vec3 edge3Cross = v1.sub(v2).cross(intersectionPoint.sub(v2));
        if (normal.dot(edge3Cross) < EPSILON) {
            return null;
        }

        // Return the intersection data
        return new Intersection(intersectionPoint, normal, this, t);
    }
}
