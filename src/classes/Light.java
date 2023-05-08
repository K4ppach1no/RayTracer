package classes;

public class Light {
    // the position of the light
    public Vec3 position;
    // the color of the light
    public Color color;
    // the intensity of the light
    public float intensity;
    // the direction of the light
    public Vec3 direction;

    // constructor
    public Light(Vec3 position, Color color, float intensity, Vec3 direction) {
        this.position = position; // the position of the light
        this.color = color; // the color of the light
        this.intensity = intensity; // the intensity of the light
        this.direction = direction; // the direction of the light
    }
}
