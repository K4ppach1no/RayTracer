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
        TopLeft = Center.add(direction.cross(new Vec3(0, 1, 0)).scale(fov / 2)).add(direction.cross(new Vec3(0, 0, 1)).scale(fov / 2));
        TopRight = Center.add(direction.cross(new Vec3(0, 1, 0)).scale(fov / 2)).sub(direction.cross(new Vec3(0, 0, 1)).scale(fov / 2));
        BottomLeft = Center.sub(direction.cross(new Vec3(0, 1, 0)).scale(fov / 2)).add(direction.cross(new Vec3(0, 0, 1)).scale(fov / 2));
    }

    public Ray getRay(float u, float v) {
        Vec3 direction = TopLeft.add(TopRight.sub(TopLeft).mul(u)).add(BottomLeft.sub(TopLeft).mul(v)).sub(position);
        return new Ray(position, direction, 0);
    }
}