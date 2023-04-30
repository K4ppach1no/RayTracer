package classes;

public class Material {
    private Color color;
    private float reflectivity;
    private float transparency;

    public Material(Color color, float reflectivity, float transparency) {
        this.color = color;
        this.reflectivity = reflectivity;
        this.transparency = transparency;
    }

    // the color is the diffuse color of the material
    public Color getColor() {
        return color;
    }

    // the reflectivity is the amount of light that is reflected by the object
    public float getReflectivity() {
        return reflectivity;
    }

    // the transparency is the amount of light that passes through the object
    public float getTransparency() {
        return transparency;
    }

    public Intersection intersect(Ray ray) {
        return null;
    }
}
