package classes;

public class Light {
    public Vec3 position;
    public Color color;
    public float intensity;

    public Light(Vec3 position, Color color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }
}
