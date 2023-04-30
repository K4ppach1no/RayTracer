package classes;

public class Vec3 {
    public float x, y, z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float dot(Vec3 normal, Vec3 direction) {
        return normal.x * direction.x + normal.y * direction.y + normal.z * direction.z;
    }

    public Vec3 cross(Vec3 vec3) {
        return new Vec3(y * vec3.z - z * vec3.y, z * vec3.x - x * vec3.z, x * vec3.y - y * vec3.x);
    }

    public static Vec3 sub(Vec3 origin, Vec3 mul) {
        return new Vec3(origin.x - mul.x, origin.y - mul.y, origin.z - mul.z);
    }

    public Vec3 sub(Vec3 v) {
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    public Vec3 add(Vec3 v) {
        return new Vec3(x + v.x, y + v.y, z + v.z);
    }

    public Vec3 scale(float t) {
        return new Vec3(x * t, y * t, z * t);
    }

    public Vec3 mul(float t) {
        return new Vec3(x * t, y * t, z * t);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public float dist(Vec3 v) {
        return (float) Math.sqrt((x - v.x) * (x - v.x) + (y - v.y) * (y - v.y) + (z - v.z) * (z - v.z));
    }

    public Vec3 normalize() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        return new Vec3(x / length, y / length, z / length);
    }

    public Vec3 negate() {
        return new Vec3(-x, -y, -z);
    }
}
