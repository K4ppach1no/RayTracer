package classes;

public class Material {
    public Color color;
    public float reflectivity;
    public float transparency;

    public Material(Color color, float reflectivity, float transparency) {
        this.color = color;
        this.reflectivity = reflectivity;
        this.transparency = transparency;
    }
}
