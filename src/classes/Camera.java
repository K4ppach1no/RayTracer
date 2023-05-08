package classes;

public class Camera {
    Vec3 position;
    Vec3 direction;
    float fov;
    Vec3 Center;
    Vec3 TopLeft, TopRight, BottomLeft;

    public Camera(Vec3 position, Vec3 direction, float fov) {
        this.position = position;
        this.direction = direction;
        this.fov = fov;
        Center = position.add(direction);
        float scale = (float) Math.tan(Math.toRadians(fov / 2));
        Vec3 right = direction.cross(new Vec3(0, 1, 0)).normalize().scale(scale);
        Vec3 up = right.cross(direction).normalize().scale(scale);
        TopLeft = Center.add(up).sub(right);
        TopRight = Center.add(up).add(right);
        BottomLeft = Center.sub(up).sub(right);
    }

    // this method returns a ray from the camera to the given pixel
    public Ray getRay(float u, float v) {
        // calculate the direction
        Vec3 direction = TopLeft.add(TopRight.sub(TopLeft).mul(u)).add(BottomLeft.sub(TopLeft).mul(v)).sub(position);
        // return the ray
        return new Ray(position, direction, Float.MAX_VALUE);
    }
}