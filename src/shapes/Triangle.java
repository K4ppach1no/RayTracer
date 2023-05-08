package shapes;



import classes.*;

public class Triangle extends Material {
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
        Vec3 edge2 = v3.subtract(v1);
        Vec3 normal = edge1.crossProduct(edge2).normalize();
    
        // Check if the ray and triangle are parallel
        float denominator = normal.dotProduct(ray.getDirection());
        if (Math.abs(denominator) < EPSILON) {
            return null;
        }
    
        // Find the point of intersection between the ray and the plane of the triangle
        Vector3D toTriangle = v1.subtract(ray.getOrigin());
        float t = toTriangle.dotProduct(normal) / denominator;
        if (t < EPSILON) {
            return null;
        }
    
        Vector3D intersectionPoint = ray.pointAt(t);
    
        // Check if the intersection point is inside the triangle
        Vector3D edge1Cross = edge1.crossProduct(intersectionPoint.subtract(v1));
        if (normal.dotProduct(edge1Cross) < EPSILON) {
            return null;
        }
    
        Vector3D edge2Cross = edge2.crossProduct(intersectionPoint.subtract(v3));
        if (normal.dotProduct(edge2Cross) < EPSILON) {
            return null;
        }
    
        Vector3D edge3Cross = v1.subtract(v2).crossProduct(intersectionPoint.subtract(v2));
        if (normal.dotProduct(edge3Cross) < EPSILON) {
            return null;
        }
    
        // Return the intersection data
        return new Intersection(intersectionPoint, normal, this, t);
    }
}
