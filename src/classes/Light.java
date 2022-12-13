package classes;

public class Light {
    public Vec3 position;
    public Color color;
    public float intensity;

    public Vec3 direction;

    public Light(Vec3 position, Color color, float intensity, Vec3 direction) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
        this.direction = direction;
    }
}
